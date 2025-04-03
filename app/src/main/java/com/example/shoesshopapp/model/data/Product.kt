package com.example.shoesshopapp.model.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product", foreignKeys = [ForeignKey(
        entity = Brand::class,
        parentColumns = ["brandId"],
        childColumns = ["brandId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    @ColumnInfo val productName: String,
    @ColumnInfo val description: String,
    @ColumnInfo val brandId: Int,
    @ColumnInfo val brandName: String,
    @ColumnInfo val price: Double,
    @ColumnInfo val image: Bitmap,
    @ColumnInfo val recommendation: Boolean
)