package com.example.shoesshopapp.model.data

import androidx.room.TypeConverter

class OrderStatusConverter {
    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.fromString(value)
    }

    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String {
        return value.toString()
    }
}