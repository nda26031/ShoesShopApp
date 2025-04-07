package com.example.shoesshopapp.model.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoesshopapp.model.data.Cart
import com.example.shoesshopapp.model.data.CartItem

data class CartWithCartItems(
    @Embedded val cart: Cart,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartId"
    ) val cartItems: List<CartItem>
)