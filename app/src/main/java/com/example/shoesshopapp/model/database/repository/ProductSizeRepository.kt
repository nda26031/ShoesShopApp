package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.database.dao.ProductDAO
import com.example.shoesshopapp.model.database.dao.ProductSizeDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase

class ProductSizeRepository(application: Application) {
    private var productSizeDao: ProductSizeDAO
    private var productDao: ProductDAO

    init {
        val database = AppDatabase.getDatabase(application)
        productSizeDao = database.getProductSizeDao()
        productDao = database.getProductDao()
    }

    suspend fun insertProductSize(productSize: ProductSize) {
        productSizeDao.insertProductSize(productSize)
    }

    suspend fun deleteProductSize(productSize: ProductSize) {
        productSizeDao.deleteProductSize(productSize)
    }

    suspend fun updateProductSize(productSize: ProductSize) {
        productSizeDao.updateProductSize(productSize)
    }

    fun getAllProductSize(): LiveData<List<ProductSize>> {
        return productSizeDao.getAllProductSize()
    }

    fun getProductSizeByProductId(productId: Int): LiveData<List<ProductSize>> = productSizeDao.getProductSizeByProductId(productId)

    fun getProductSizeById(productSizeId: Int): LiveData<ProductSize> = productSizeDao.getProductSizeById(productSizeId)

    fun getProductById(productId: Int): LiveData<Product> = productDao.getProductById(productId)
}