package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_item", foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["orderId"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )]

)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val orderItemId: Int = 0,
    val orderId: Int,
    val productId: Int,
    val productSizeId: Int,
    val quantity: Int
)
