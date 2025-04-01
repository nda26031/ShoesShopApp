package com.example.shoesshopapp.model.data.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Product

data class BrandWithProducts(
    @Embedded val brand: Brand,
    @Relation(
        parentColumn = "brandId",
        entityColumn = "brandId"
    ) val products: List<Product>
)