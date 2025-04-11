package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = Cart::class,
            parentColumns = ["cartId"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductSize::class,
            parentColumns = ["productSizeId"],
            childColumns = ["productSizeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true) val cartItemId: Int = 0,
    val cartId: Int,
    val productId: Int,
    val productSizeId: Int,
    val quantity: Int = 1
)