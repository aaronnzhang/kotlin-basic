package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.model.Inventory
import com.thoughtworks.kotlin_basic.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProductRepositoryTest {

    private val mockApiService = mockk<ApiService>()

    @Test
    fun `test calculate price for HIGH_DEMAND product with stock greater than 100`() = runBlocking {
        // Arrange
        val products = listOf(
            Product(sku = "SKU123", name = "Product 1", price = 100.0, type = "HIGH_DEMAND", imageUrl = "image_url")
        )
        val inventories = listOf(
            Inventory(sku = "SKU123", region = "Region 1", stock = 101)
        )

        coEvery { mockApiService.getProducts() } returns products
        coEvery { mockApiService.getInventories() } returns inventories

        val repository = ProductRepository(mockApiService)

        // Act
        val result = repository.getProductsWithStock()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(100.0, result[0].price)  // Original price
    }

    @Test
    fun `test calculate price for HIGH_DEMAND product with stock between 31 and 100`() = runBlocking {
        // Arrange
        val products = listOf(
            Product(sku = "SKU123", name = "Product 1", price = 100.0, type = "HIGH_DEMAND", imageUrl = "image_url")
        )
        val inventories = listOf(
            Inventory(sku = "SKU123", region = "Region 1", stock = 50)
        )

        coEvery { mockApiService.getProducts() } returns products
        coEvery { mockApiService.getInventories() } returns inventories

        val repository = ProductRepository(mockApiService)

        // Act
        val result = repository.getProductsWithStock()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(120.0, result[0].price)  // 120% of original price
    }

    @Test
    fun `test calculate price for HIGH_DEMAND product with stock less than 30`() = runBlocking {
        // Arrange
        val products = listOf(
            Product(sku = "SKU123", name = "Product 1", price = 100.0, type = "HIGH_DEMAND", imageUrl = "image_url")
        )
        val inventories = listOf(
            Inventory(sku = "SKU123", region = "Region 1", stock = 20)
        )

        coEvery { mockApiService.getProducts() } returns products
        coEvery { mockApiService.getInventories() } returns inventories

        val repository = ProductRepository(mockApiService)

        // Act
        val result = repository.getProductsWithStock()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(150.0, result[0].price)  // 150% of original price
    }

    @Test
    fun `test calculate price for NORMAL product`() = runBlocking {
        // Arrange
        val products = listOf(
            Product(sku = "SKU123", name = "Product 1", price = 100.0, type = "NORMAL", imageUrl = "image_url")
        )
        val inventories = listOf(
            Inventory(sku = "SKU123", region = "Region 1", stock = 50)
        )

        coEvery { mockApiService.getProducts() } returns products
        coEvery { mockApiService.getInventories() } returns inventories

        val repository = ProductRepository(mockApiService)

        // Act
        val result = repository.getProductsWithStock()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(100.0, result[0].price)  // Original price for NORMAL product
    }
}
