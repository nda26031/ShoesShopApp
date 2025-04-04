package com.example.shoesshopapp.model.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand")
data class Brand(
    @PrimaryKey(autoGenerate = true) val brandId: Int = 0,
    @ColumnInfo(name = "brand_name", index = true) val brandName: String,
    @ColumnInfo val brandLogo: Bitmap
) {
}