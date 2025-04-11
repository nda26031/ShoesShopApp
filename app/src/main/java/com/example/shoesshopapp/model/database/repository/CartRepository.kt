package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Cart
import com.example.shoesshopapp.model.data.relationship.CartWithCartItems
import com.example.shoesshopapp.model.database.dao.CartDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase

class CartRepository(application: Application) {
    private var cartDao: CartDAO

    init {
        val database = AppDatabase.getDatabase(application)
        cartDao = database.getCartDao()
    }

    fun getCartByUserId(userId: Int): LiveData<Cart> = cartDao.getCartByUserId(userId)

    fun getCartWithCartItems(cartId: Int): LiveData<CartWithCartItems> =
        cartDao.getCartWithCartItems(cartId)
}