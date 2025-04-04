package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.database.dao.BrandDAO
import com.example.shoesshopapp.model.database.dao.ProductDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(application: Application) {
    private val productDAO: ProductDAO
    private val brandDAO: BrandDAO

    init {
        val database = AppDatabase.getDatabase(application)
        productDAO = database.getProductDao()
        brandDAO = database.getBrandDao()
    }

    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        productDAO.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        productDAO.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        productDAO.deleteProduct(product)
    }

    fun getAllProducts(): LiveData<List<Product>> = productDAO.getAllProduct()

    fun getProductById(productId: Int): LiveData<Product> = productDAO.getProductById(productId)

    fun getAllBrands(): LiveData<List<Brand>> = brandDAO.getAllBrands()

    fun getAllRecommendedProduct(): LiveData<List<Product>> = productDAO.getAllRecommendedProduct()

    fun searchProducts(searchQuery: String): LiveData<List<Product>> =
        productDAO.searchProducts("%$searchQuery%")
}