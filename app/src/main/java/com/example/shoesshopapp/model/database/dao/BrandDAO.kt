package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.relationship.BrandWithProducts

@Dao
interface BrandDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrand(brand: Brand): Long

    @Query("SELECT EXISTS(SELECT * FROM brand WHERE brandName = :brandName)")
    suspend fun isExistBrand(brandName: String): Int

    @Query("SELECT * FROM brand WHERE brandName LIKE :brandName")
    fun searchBrand(brandName: String): LiveData<List<Brand>>

    @Delete
    suspend fun deleteBrand(brand: Brand)

    @Update
    suspend fun updateBrand(brand: Brand): Int

    @Query("UPDATE brand SET brandName = :brandName WHERE brandId = :brandId")
    suspend fun updateBrandName(brandId: Int, brandName: String): Int

    @Query("UPDATE brand SET brandLogo = :brandLogo WHERE brandId = :brandId")
    suspend fun updateBrandLogo(brandId: Int, brandLogo: ByteArray): Int

    @Query("SELECT * FROM brand")
    fun getAllBrand(): LiveData<List<Brand>>

    @Query("SELECT * FROM brand WHERE brandId = :brandId")
    suspend fun getBrandById(brandId: Int): Brand?

    @Transaction
    @Query("SELECT * FROM brand WHERE brandId =:brandId")
    fun getBrandWithProducts(brandId: Int): LiveData<List<BrandWithProducts>>
}