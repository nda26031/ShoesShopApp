package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shoesshopapp.model.data.ProductSize

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

    @Query("SELECT * FROM product_size WHERE productSizeId = :productSizeId")
    fun getProductSizeById(productSizeId: Int): LiveData<ProductSize>

    @Query("SELECT * FROM product_size WHERE productId = :productId")
    fun getProductSizeByProductId(productId: Int): LiveData<List<ProductSize>>

    @Query("UPDATE product_size SET isSelect = :isSelected WHERE productSizeId = :productSizeId")
    suspend fun setSelectProductSize(productSizeId: Int, isSelected: Boolean)

    @Query("UPDATE product_size SET isSelect = 0 WHERE productId = :productId")
    suspend fun deselectAllSizesForProduct(productId: Int)

    @Transaction
    suspend fun selectSingleSize(productId: Int, productSizeId: Int) {
        deselectAllSizesForProduct(productId)
        setSelectProductSize(productSizeId, true)
    }

    @Query("UPDATE product_size SET quantity = quantity - :quantity WHERE productSizeId = :productSizeId")
    suspend fun updateProductSizeQuantity(productSizeId: Int, quantity: Int)
}