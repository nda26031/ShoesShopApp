package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.addBrand

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.database.repository.BrandRepository
import kotlinx.coroutines.launch

class AddBrandViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)

    fun insertBrand(brand: Brand) {
        viewModelScope.launch {
            brandRepository.insertBrand(brand)
        }
    }

    class AddBrandViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddBrandViewModel::class.java)) return AddBrandViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}