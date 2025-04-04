package com.example.shoesshopapp.ui.fragment.users.brand

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.database.repository.BrandRepository

class BrandViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)

    fun getAllBrands(): LiveData<List<Brand>> = brandRepository.getAllBrands()

    fun searchBrands(searchQuery:String):LiveData<List<Brand>> = brandRepository.searchBrands(searchQuery)

    class BrandViewModelFactory(private val application: Application) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BrandViewModel::class.java)) {
                return BrandViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}