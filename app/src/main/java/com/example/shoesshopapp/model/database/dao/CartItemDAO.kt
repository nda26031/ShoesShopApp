package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoesshopapp.model.data.CartItem

@Dao
interface CartItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem): Long

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE cartItemId = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE cartItemId = :cartItemId")
    suspend fun updateCartItemQuantity(cartItemId: Int, quantity: Int)

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    fun getAllCartItemsByCartId(cartId: Int): LiveData<List<CartItem>>
}