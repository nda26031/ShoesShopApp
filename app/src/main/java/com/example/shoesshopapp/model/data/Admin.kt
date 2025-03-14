package com.example.shoesshopapp.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "admins", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Admin(
    @PrimaryKey(autoGenerate = true) val adminId: Int = 0,
    val accountId: Int,
    val adminLevel: String
)