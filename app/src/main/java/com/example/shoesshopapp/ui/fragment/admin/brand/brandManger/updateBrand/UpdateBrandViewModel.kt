package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.updateBrand

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.database.repository.BrandRepository
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel
import kotlinx.coroutines.launch

class UpdateBrandViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)

    fun updateBrand(brand: Brand) {
        viewModelScope.launch {
            brandRepository.updateBrand(brand)
        }
    }

    fun getBrandById(brandId: Int) : LiveData<Brand> = brandRepository.getBrandById(brandId)

    class UpdateBrandViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateBrandViewModel::class.java)) return UpdateBrandViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

}