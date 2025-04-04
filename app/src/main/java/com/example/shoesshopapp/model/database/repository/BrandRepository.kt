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

    fun searchBrands(searchQuery: String): LiveData<List<Brand>> =
        brandDao.searchBrands(searchQuery)

    fun getAllBrands(): LiveData<List<Brand>> = brandDao.getAllBrands()


    fun getBrandById(brandId: Int): LiveData<Brand> = brandDao.getBrandById(brandId)

//    private val brandDao: BrandDAO
//
//    init {
//        val database = AppDatabase.getDatabase(application)
//        brandDao = database.getBrandDao()
//    }
//
//    fun getAllBrands(): LiveData<List<Brand>> = brandDao.getAllBrand()
//
//    fun getBrandWithProducts(brandId: Int) = brandDao.getBrandWithProducts(brandId)
//
//    fun searchBrand(brandName: String) = brandDao.searchBrand(brandName)
//
//    suspend fun insertBrand(brand: Brand) {
//        withContext(Dispatchers.IO) {
//            brandDao.insertBrand(brand)
//        }
//    }
//
//    private suspend fun isExistBrand(brandName: String): Boolean {
//        return withContext(Dispatchers.IO) {
//            brandDao.isExistBrand(brandName) > 0
//        }
//    }
//
//    suspend fun insertBrandIfNotExists(brand: Brand): Boolean {
//        return if (!isExistBrand(brand.brandName)) {
//            brandDao.insertBrand(brand)
//            true
//        } else {
//            false
//        }
//    }
//
//    suspend fun getBrandById(brandId: Int): Brand? {
//        return withContext(Dispatchers.IO) {
//            brandDao.getBrandById(brandId)
//        }
//    }
//
//    suspend fun updateBrand(brand: Brand) {
//        withContext(Dispatchers.IO) {
//            brandDao.updateBrand(brand)
//        }
//    }
//
//    suspend fun updateBrandWithCheck(
//        brandId: Int,
//        brandName: String,
//        brandLogo: ByteArray
//    ): Boolean {
//        return withContext(Dispatchers.IO) {
//
//            val existingBrand = isExistBrand(brandName)
//            if (existingBrand) {
//                return@withContext false
//            }
//
//            val currentBrand = brandDao.getBrandById(brandId)
//            if (currentBrand != null) {
//                val updatedBrand = currentBrand.copy(
//                    brandName = brandName,
//                    brandLogo = brandLogo
//                )
//                brandDao.updateBrand(updatedBrand)
//                true
//            } else {
//                false
//            }
//        }
//    }
//
//    suspend fun deleteBrand(brand: Brand) {
//        withContext(Dispatchers.IO) {
//            brandDao.deleteBrand(brand)
//        }
//    }
//
//    suspend fun deleteBrandById(brandId: Int): Boolean {
//        return withContext(Dispatchers.IO) {
//            val brand = brandDao.getBrandById(brandId)
//            if (brand != null) {
//                brandDao.deleteBrand(brand)
//                true
//            } else {
//                false
//            }
//        }
//    }
}