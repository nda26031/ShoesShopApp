package com.example.shoesshopapp.model.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize

data class ProductWithSizes(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    ) val sizes: List<ProductSize>
)