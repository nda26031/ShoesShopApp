package com.example.shoesshopapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favourite_product", foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["productId", "userId"]
)
data class FavouriteProduct(
    @ColumnInfo val userId: Int,
    @ColumnInfo val productId: Int
)