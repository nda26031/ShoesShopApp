package com.example.shoesshopapp.ui.fragment.admin.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.database.repository.ProductRepository
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel
import kotlinx.coroutines.launch

class ProductMangerViewModel(application: Application): AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    fun deleteBrand(product: Product) {
        viewModelScope.launch {
            productRepository.deleteProduct(product)
        }
    }

    fun getAllBrands(): LiveData<List<Product>> = productRepository.getAllProducts()

    class ProductViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductMangerViewModel::class.java)) return ProductMangerViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}