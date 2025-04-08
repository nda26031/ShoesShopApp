package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.data.relationship.ProductWithSizes
import com.example.shoesshopapp.model.database.repository.ProductRepository
import com.example.shoesshopapp.model.database.repository.ProductSizeRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)
    private val productSizeRepository = ProductSizeRepository(application)

    fun getProductWithSizes(productId: Int): LiveData<ProductWithSizes> =
        productRepository.getProductWithSizes(productId)

    fun getProductWithAvailableSizes(productId: Int): LiveData<ProductWithSizes> {
        return productRepository.getProductWithAvailableSizes(productId)
    }

    fun selectSingleProductSize(productSize: ProductSize) {
        viewModelScope.launch {
            productSizeRepository.selectSingleProductSize(productSize)
            Log.d("ProductDetailFragment", "isSelect: ${productSize.isSelect}")

        }
    }

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