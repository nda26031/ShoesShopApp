package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Cart
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.UIState
import com.example.shoesshopapp.model.database.repository.BrandRepository
import com.example.shoesshopapp.model.database.repository.CartRepository
import com.example.shoesshopapp.model.database.repository.ProductRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)
    private val productRepository = ProductRepository(application)
    private val cartRepository = CartRepository(application)

    fun getAllBrands(): LiveData<List<Brand>> = brandRepository.getAllBrands()

    fun getAllRecommendedProduct(): LiveData<List<Product>> =
        productRepository.getAllRecommendedProduct()

    fun getCartByUserId(userId: Int): LiveData<Cart> {
        return cartRepository.getCartByUserId(userId)
    }

    class DashboardModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) return DashboardViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

}