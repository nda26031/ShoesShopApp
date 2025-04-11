package com.example.shoesshopapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.shoesshopapp.model.data.OrderItem

@Dao
interface OrderItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItem)

    @Insert
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
}