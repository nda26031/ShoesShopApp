package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.updateSize

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.database.repository.ProductSizeRepository
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.addSize.AddSizeViewModel
import kotlinx.coroutines.launch

class UpdateSizeViewModel(application: Application) : AndroidViewModel(application) {
    private val productSizeRepository = ProductSizeRepository(application)

    fun updateProductSize(productSize: ProductSize) {
        viewModelScope.launch {
            productSizeRepository.updateProductSize(productSize)
        }
    }

    fun getProductSize(size: String): LiveData<ProductSize> =
        productSizeRepository.getProductSize(size)

    class UpdateSizeViewModelFactory(private val application: Application):
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateSizeViewModel::class.java)) return UpdateSizeViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

}