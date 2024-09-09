package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.model.ProductWithStock

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProductsWithStock(): List<ProductWithStock> {
        val products = apiService.getProducts()
        val inventories = apiService.getInventories()

        val stockMap = inventories.groupBy { it.SKU }
            .mapValues { entry -> entry.value.sumBy { it.quantity } }

        return products.map { product ->
            val totalStock = stockMap[product.SKU] ?: 0
            val finalPrice = calculatePrice(product.type, product.price, totalStock)
            ProductWithStock(product.SKU, product.name, finalPrice, totalStock, product.image)
        }
    }

    private fun calculatePrice(type: String, originalPrice: Double, stock: Int): Double {
        return when (type) {
            "HIGH_DEMAND" -> when {
                stock > 100 -> originalPrice
                stock in 31..100 -> originalPrice * 1.2
                else -> originalPrice * 1.5
            }

            else -> originalPrice
        }
    }
}
