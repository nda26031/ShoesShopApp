package com.example.shoesshopapp.model.database.repository

import android.app.Application
import com.example.shoesshopapp.model.database.dao.OrderDAO
import com.example.shoesshopapp.model.database.dao.OrderItemDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase

class OderItemRepository(application: Application) {
    private val orderItemDao: OrderItemDAO
    private val orderDao: OrderDAO

    init {
        val database = AppDatabase.getDatabase(application)
        orderItemDao = database.getOrderItemDao()
        orderDao = database.getOrderDao()
    }
}