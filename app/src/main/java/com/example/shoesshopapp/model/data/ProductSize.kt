package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_size", foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class ProductSize(
    @PrimaryKey(autoGenerate = true)
    val productSizeId: Int = 0,
    val productId: Int,
    val size: String,
    val quantity: Int,
    var isSelect: Boolean = false
)