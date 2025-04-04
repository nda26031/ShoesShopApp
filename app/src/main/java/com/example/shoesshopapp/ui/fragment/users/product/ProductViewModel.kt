package com.example.shoesshopapp.ui.fragment.users.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.database.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    fun getAllProducts(): LiveData<List<Product>> = productRepository.getAllProducts()

    fun searchProducts(searchQuery: String): LiveData<List<Product>> =
        productRepository.searchProducts(searchQuery)

    class ProductViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                return ProductViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}