package com.example.shoesshopapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoesshopapp.model.data.Admin

@Dao
interface AdminDAO {
    @Insert
    suspend fun insertAdmin(admin: Admin)

    @Query("SELECT * FROM admins WHERE accountId = :accountId")
    suspend fun getAdminByAccountId(accountId: Int): Admin?
}