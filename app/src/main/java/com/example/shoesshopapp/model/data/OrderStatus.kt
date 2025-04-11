package com.example.shoesshopapp.model.data

enum class OrderStatus {
    PENDING, DELIVERY, FINISH;

    override fun toString(): String {
        return name
    }

    companion object {
        // Helper to convert string back to enum
        fun fromString(value: String): OrderStatus {
            return valueOf(value)
        }
    }
}