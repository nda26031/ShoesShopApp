package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoesshopapp.model.data.Brand

@Dao
interface BrandDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBrand(brand: Brand)

    @Update
    suspend fun updateBrand(brand: Brand)

    @Delete
    suspend fun deleteBrand(brand: Brand)

    @Query("SELECT * FROM brand")
    fun getAllBrands(): LiveData<List<Brand>>

    @Query("SELECT * FROM brand WHERE brandId = :id")
    fun getBrandById(id: Int): LiveData<Brand>

    @Query("SELECT * FROM brand WHERE brand_name LIKE :searchQuery")
    fun searchBrands(searchQuery: String): LiveData<List<Brand>>
//
//    @Transaction
//    @Query("SELECT * FROM brand")
//    fun getBrandsWithProducts(): LiveData<List<BrandWithProducts>>
}