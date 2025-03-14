package com.example.shoesshopapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoesshopapp.model.data.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE accountId = :accountId")
    suspend fun getUserByAccount(accountId: Int): User
}