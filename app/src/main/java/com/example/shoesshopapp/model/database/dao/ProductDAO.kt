package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.relationship.ProductWithSizes

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE productName LIKE :searchQuery")
    fun searchProducts(searchQuery: String): LiveData<List<Product>>

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE productId = :productId")
    fun getProductById(productId: Int): LiveData<Product>

    @Query("SELECT * FROM product WHERE recommendation = 1")
    fun getAllRecommendedProduct(): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM product WHERE productId = :productId")
    fun getProductWithSizes(productId: Int): LiveData<ProductWithSizes>
}