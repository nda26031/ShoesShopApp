package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.database.repository.ProductRepository

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    fun getProductWithSizes(productId: Int) = productRepository.getProductWithSizes(productId)

    class ProductDetailViewModelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}