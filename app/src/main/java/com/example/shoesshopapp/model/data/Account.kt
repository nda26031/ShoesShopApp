package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
open class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val role : String
)