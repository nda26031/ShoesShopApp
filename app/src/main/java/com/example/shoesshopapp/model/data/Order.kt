package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val userId: Int,
    val orderCode: String,
    val orderDate: Long = System.currentTimeMillis(),
    val orderStatus: OrderStatus,
    val totalCost: Double
)
