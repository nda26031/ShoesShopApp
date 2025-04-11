package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )]
)

data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,
    val userId: Int
)