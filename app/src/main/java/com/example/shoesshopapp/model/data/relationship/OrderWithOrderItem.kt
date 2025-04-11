package com.example.shoesshopapp.model.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.data.OrderItem


data class OrderWithOrderItem(
    @Embedded
    val order: Order,
    @Relation(
        entity = OrderItem::class,
        parentColumn = "orderId",
        entityColumn = "orderId"
    ) val orderItems: List<OrderItemWithDetails>
)