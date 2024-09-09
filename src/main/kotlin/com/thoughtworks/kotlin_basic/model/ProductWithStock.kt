package com.thoughtworks.kotlin_basic.model

data class ProductWithStock(
    val sku: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String
)
