package com.example.shoesshopapp.model.dataTest

data class Order(
    val id: Int,
    val userName: String,
    val orderCode: String,
    val price: Double,
    val imageUrl: Int,
    val orderDate: String,
    val orderStatus: String
)