package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.UIState
import com.example.shoesshopapp.model.database.repository.BrandRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val brandRepository = BrandRepository(application)

    fun getAllBrands(): LiveData<List<Brand>> = brandRepository.getAllBrands()

    class DashboardModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) return DashboardViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

}