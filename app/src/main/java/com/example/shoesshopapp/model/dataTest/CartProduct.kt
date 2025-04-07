package com.example.shoesshopapp.model.dataTest

data class CartProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: Int
) {
    fun getTotalPrice(): Double {
        return price * quantity
    }
}