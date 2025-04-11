package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.database.repository.ProductSizeRepository
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct.UpdateProductViewModel
import kotlinx.coroutines.launch

class SizeManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val productSizeRepository = ProductSizeRepository(application)


    fun deleteProductSize(productSize: ProductSize) {
        viewModelScope.launch {
            productSizeRepository.deleteProductSize(productSize)
        }
    }

    fun getProductById(productId: Int): LiveData<Product> =
        productSizeRepository.getProductById(productId)

    fun getProductSizeByProductId(productId: Int): LiveData<List<ProductSize>> =
        productSizeRepository.getProductSizeByProductId(productId)

    class SizeManagerViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SizeManagerViewModel::class.java)) return SizeManagerViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}