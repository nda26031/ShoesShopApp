package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.data.relationship.OrderWithOrderItem

@Dao
interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT * FROM `order`")
    fun getAllOrder(): LiveData<List<Order>>

    @Query("SELECT * FROM `order` WHERE userId = :userId")
    fun getOrderByUserId(userId: Int): LiveData<List<Order>>

    @Transaction
    @Query("SELECT * FROM `order` WHERE orderId = :orderId")
    fun getOrderWithOrderItem(orderId: Int): LiveData<OrderWithOrderItem>
}