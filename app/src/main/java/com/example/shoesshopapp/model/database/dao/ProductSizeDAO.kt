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
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.data.relationship.ProductWithSizes

@Dao
interface ProductSizeDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertProductSize(productSize: ProductSize)

    @Delete
    suspend fun deleteProductSize(productSize: ProductSize)

    @Update
    suspend fun updateProductSize(productSize: ProductSize)

    @Query("SELECT * FROM product_size")
    fun getAllProductSize(): LiveData<List<ProductSize>>

    @Query("SELECT * FROM product_size WHERE size = :size")
    fun getProductSize(size: String): LiveData<ProductSize>

    @Query("SELECT * FROM product_size WHERE productId = :productId")
    fun getProductSizeByProductId(productId: Int): LiveData<List<ProductSize>>

    @Transaction
    @Query("SELECT * FROM product_size WHERE productId = :productId")
    suspend fun getProductSizeWithProductId(productId: Int): List<ProductWithSizes>

}