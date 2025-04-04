package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.addSize

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.database.repository.ProductSizeRepository
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct.UpdateProductViewModel
import kotlinx.coroutines.launch

class AddSizeViewModel(application: Application) : AndroidViewModel(application) {
    private val productSizeRepository = ProductSizeRepository(application)

    fun insertProductSize(productSize: ProductSize) {
        viewModelScope.launch {
            productSizeRepository.insertProductSize(productSize)
        }
    }

    class AddSizeViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddSizeViewModel::class.java)) return AddSizeViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}