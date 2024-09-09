import com.thoughtworks.kotlin_basic.service.ApiService
import com.thoughtworks.kotlin_basic.service.ProductRepository
import com.thoughtworks.kotlin_basic.model.ProductWithStock
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun displayProducts(products: List<ProductWithStock>) {
    products.forEach { product ->
        println("SKU: ${product.sku}, Name: ${product.name}, Price: ${product.price}, Stock: ${product.stock}, Image: ${product.imageUrl}")
    }
}

fun createRetrofitService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}

fun main() = runBlocking {
    val apiService = createRetrofitService()
    val repository = ProductRepository(apiService)

    val productsWithStock = repository.getProductsWithStock()
    displayProducts(productsWithStock)
}
