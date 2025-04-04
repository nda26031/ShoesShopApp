package com.example.shoesshopapp.ui.fragment.admin.brand

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.database.repository.BrandRepository
import kotlinx.coroutines.launch

class BrandManagerViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)

    fun deleteBrand(brand: Brand) {
        viewModelScope.launch {
            brandRepository.deleteBrand(brand)
        }
    }

    fun searchBrands(searchQuery: String): LiveData<List<Brand>> = brandRepository.searchBrands(searchQuery)

    fun getAllBrands(): LiveData<List<Brand>> = brandRepository.getAllBrands()

    class BrandViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BrandManagerViewModel::class.java)) return BrandManagerViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}