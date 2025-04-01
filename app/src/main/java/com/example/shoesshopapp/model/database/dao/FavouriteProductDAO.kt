package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoesshopapp.model.data.FavouriteProduct
import com.example.shoesshopapp.model.data.Product

@Dao
interface FavouriteProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteProduct(favouriteProduct: FavouriteProduct)

    @Query("DELETE FROM favourite_product WHERE userId = :userId AND productId = :productId")
    suspend fun deleteFavouriteProduct(userId: Int, productId: Int)

    @Query("SELECT product.* FROM product INNER JOIN favourite_product ON product.productId = favourite_product.productId WHERE favourite_product.userId = :userId")
    fun getFavouriteProducts(userId: Int): LiveData<List<Product>>

}