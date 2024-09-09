package com.thoughtworks.kotlin_basic.model

data class Product(
    val id: Int,
    val SKU: String,
    val name: String,
    val price: Double,
    val type: String,
    val image: String
)
