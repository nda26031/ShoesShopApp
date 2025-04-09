package com.example.shoesshopapp.model.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize


data class CartItemWithDetails(
    @Embedded
    val cartItem: CartItem,
    @Relation(
        entity = Product::class,
        parentColumn = "productId",
        entityColumn = "productId"
    ) val product: Product,
    @Relation(
        entity = ProductSize::class,
        parentColumn = "productSizeId",
        entityColumn = "productSizeId"
    ) val productSize: ProductSize
)