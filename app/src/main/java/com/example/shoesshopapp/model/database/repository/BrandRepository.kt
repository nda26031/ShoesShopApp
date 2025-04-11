package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.database.dao.BrandDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrandRepository(application: Application) {
    private val brandDao: BrandDAO

    init {
        val database = AppDatabase.getDatabase(application)
        brandDao = database.getBrandDao()
    }

    suspend fun insertBrand(brand: Brand) = withContext(Dispatchers.IO) {
        brandDao.insertBrand(brand)
    }

    suspend fun updateBrand(brand: Brand) = withContext(Dispatchers.IO) {
        brandDao.updateBrand(brand)
    }

    suspend fun deleteBrand(brand: Brand) = withContext(Dispatchers.IO) {
        brandDao.deleteBrand(brand)
    }

    fun getAllBrands(): LiveData<List<Brand>> = brandDao.getAllBrands()

    fun getBrandById(brandId: Int): LiveData<Brand> = brandDao.getBrandById(brandId)

    fun searchBrands(searchQuery: String): LiveData<List<Brand>> =
        brandDao.searchBrands("%$searchQuery%")

}