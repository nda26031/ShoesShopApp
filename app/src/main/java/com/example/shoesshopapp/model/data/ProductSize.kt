package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "product_size", foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["productId","size"]
)
data class ProductSize(
    val productId: Int,
    val size: String,
    val quantity: Int,
)