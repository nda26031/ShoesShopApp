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

//
//    private val _state = MutableLiveData<UIState>(UIState.Loading)
//    val state: LiveData<UIState> = _state
//
//
//    fun getAllBrands(): LiveData<List<Brand>> = brandRepository.getAllBrands()
//
//    fun insertBrand(brandName: String, brandLogo: Uri) {
//        viewModelScope.launch {
//            try {
//                val brandLogoByte = getApplication<Application>().contentResolver
//                    .openInputStream(brandLogo)?.use {
//                        it.readBytes()
//                    }
//                    ?: throw IOException("Could not read image")
//
//                val brand = Brand(
//                    brandName = brandName,
//                    brandLogo = brandLogoByte
//                )
//                val insertResult = brandRepository.insertBrandIfNotExists(brand)
//                if (!insertResult) {
//                    _state.value =
//                        UIState.Error("Brand could not be inserted. Is it already exists?")
//                } else {
//                    _state.value = UIState.Success("Brand added successfully")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun updateBrand(brandId: Int, brandName: String, brandLogo: Uri){
//        viewModelScope.launch {
//            try {
//                val brandLogoByte = getApplication<Application>().contentResolver
//                    .openInputStream(brandLogo)?.use {
//                        it.readBytes()
//                    }
//                    ?: throw IOException("Could not read image")
//
//                val success = brandRepository.updateBrandWithCheck(
//                    brandId = brandId,
//                    brandName = brandName,
//                    brandLogo = brandLogoByte
//                )
//                if (success) {
//                    _state.value = UIState.Success("Brand updated successfully")
//                } else {
//                    _state.value = UIState.Error("Brand name already exists or brand not found")
//                }
//
//            }catch (e: Exception) {
//                _state.value = UIState.Error(e.message ?: "Error updating brand")
//                e.printStackTrace()
//            }
//        }
//
//    }
//
//    private val _currentBrand = MutableLiveData<Brand?>()
//    val currentBrand: LiveData<Brand?> = _currentBrand
//
//    fun getBrandById(brandId: Int) {
//        viewModelScope.launch {
//            try {
//                _state.value = UIState.Loading
//                val brand = brandRepository.getBrandById(brandId)
//                _currentBrand.value = brand
//                _state.value = if (brand != null) UIState.Success("Brand loaded") else UIState.Error("Brand not found")
//            } catch (e: Exception) {
//                _state.value = UIState.Error(e.message ?: "Error loading brand")
//                _currentBrand.value = null
//            }
//        }
//    }
//
////    fun updateBrand(brand: Brand) {
////        viewModelScope.launch {
////            brandRepository.updateBrand(brand)
////        }
////    }
//
//    fun deleteBrand(brand: Brand) {
//        viewModelScope.launch {
//            brandRepository.deleteBrand(brand)
//        }
//    }
//
//    fun deleteBrandById(brandId: Int) {
//        viewModelScope.launch {
//            try {
//                _state.value = UIState.Loading
//
//                val success = brandRepository.deleteBrandById(brandId)
//                if (success) {
//                    _state.value = UIState.Success("Brand deleted successfully")
//                } else {
//                    _state.value = UIState.Error("Brand not found")
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }