package com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.database.repository.ProductRepository
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.addProduct.AddProductViewModel
import kotlinx.coroutines.launch

class UpdateProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productRepository.updateProduct(product)
        }
    }

    fun getProductById(productId: Int): LiveData<Product> =
        productRepository.getProductById(productId)

    fun getAllBrands(): LiveData<List<Brand>> = productRepository.getAllBrands()

    class UpdateProductViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateProductViewModel::class.java)) return UpdateProductViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}