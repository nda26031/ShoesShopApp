package com.example.shoesshopapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoesshopapp.model.data.Account

@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account): Long

    @Query("SELECT * FROM accounts WHERE email = :email AND password = :password")
    suspend fun getAccount(email: String, password: String): Account?

    @Query("SELECT COUNT(*) FROM accounts WHERE role = 'admin'")
    suspend fun getAdminCount(): Int

    @Query("SELECT COUNT(*) FROM accounts WHERE email = :email")
    suspend fun isEmailExist(email: String): Int
}