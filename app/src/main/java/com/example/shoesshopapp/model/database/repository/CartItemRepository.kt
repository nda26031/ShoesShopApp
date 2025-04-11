package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.database.dao.CartItemDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartItemRepository(application: Application) {
    private var cartItemDAO: CartItemDAO

    init {
        val database = AppDatabase.getDatabase(application)
        cartItemDAO = database.getCartItemDao()
    }

    suspend fun insertCartItem(cartItem: CartItem): Long = withContext(Dispatchers.IO) {
        cartItemDAO.insertCartItem(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItem) = withContext(Dispatchers.IO) {
        cartItemDAO.updateCartItem(cartItem)
    }

    suspend fun deleteCartItemById(cartItemId: Int) = withContext(Dispatchers.IO) {
        cartItemDAO.deleteCartItemById(cartItemId)
    }

    suspend fun deleteCartItem(cartItem: CartItem) = withContext(Dispatchers.IO) {
        cartItemDAO.deleteCartItem(cartItem)
    }

    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int) =
        withContext(Dispatchers.IO) {
            if (quantity <= 0) {
                deleteCartItemById(cartItemId)
            } else {
                cartItemDAO.updateCartItemQuantity(cartItemId, quantity)
            }
        }

    suspend fun updateCartItemQuantity2(cartItem: CartItem) =
        withContext(Dispatchers.IO) {
            if (cartItem.quantity <= 0) {
                deleteCartItemById(cartItem.cartItemId)
            } else {
                cartItemDAO.updateCartItem(cartItem)
            }
        }

    fun getAllCartItemsByCartId(cartId: Int): LiveData<List<CartItem>> {
        return cartItemDAO.getAllCartItemsByCartId(cartId)
    }
}