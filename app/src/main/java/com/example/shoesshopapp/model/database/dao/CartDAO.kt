package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.shoesshopapp.model.data.Cart
import com.example.shoesshopapp.model.data.relationship.CartWithCartItems

@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Query("SELECT * FROM cart WHERE userId = :userId")
    fun getCartByUserId(userId: Int): LiveData<Cart>

    @Transaction
    @Query("SELECT * FROM cart WHERE cartId = :cartId")
    fun getCartWithCartItems(cartId: Int): LiveData<CartWithCartItems>
}