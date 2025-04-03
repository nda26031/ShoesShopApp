package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE productName LIKE :productName")
    fun searchProduct(productName: String): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE productId = :productId")
    fun getProductById(productId: Int): LiveData<Product>

    @Query("SELECT * FROM product WHERE recommendation = 1")
    fun getRecommendationProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE productName = :productName AND brandId=:brandId LIMIT 1")
    fun getProductByNameAndBrand(productName: String, brandId: Int): Product?

    @Query("DELETE FROM product_size WHERE productId = :productId")
    suspend fun deleteProductSizes(productId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSize(productSize: ProductSize)

    @Transaction
    suspend fun addProductWithSizes(product: Product, size: ProductSize): Int {
        val existingProduct = getProductByNameAndBrand(product.productName, product.brandId)

        return if (existingProduct != null) {
            insertSize(size.copy(productId = existingProduct.productId))
            existingProduct.productId
        } else {
            val newProductId = insertProduct(product).toInt()
            insertSize(size.copy(productId = newProductId))
            newProductId
        }

    }
}