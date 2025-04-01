package com.example.shoesshopapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand")
data class Brand(
    @PrimaryKey(autoGenerate = true) val brandId: Int = 0,
    @ColumnInfo val brandName: String,
    @ColumnInfo val brandLogo: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Brand

        if (brandId != other.brandId) return false
        if (brandName != other.brandName) return false
        if (!brandLogo.contentEquals(other.brandLogo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = brandId
        result = 31 * result + brandName.hashCode()
        result = 31 * result + brandLogo.contentHashCode()
        return result
    }
}