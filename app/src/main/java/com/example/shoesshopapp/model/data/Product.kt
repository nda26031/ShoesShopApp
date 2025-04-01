package com.example.shoesshopapp.model.data

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
    @ColumnInfo val brandId: Int,
    @ColumnInfo val price: Double,
    @ColumnInfo val description: String,
    @ColumnInfo val image: Int,
    @ColumnInfo val recommendation: Boolean
)