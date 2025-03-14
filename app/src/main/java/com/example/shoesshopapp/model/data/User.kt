package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val accountId: Int,
    val fullName: String,
    val address: String,
    val phone: String
)