package com.example.shoesshopapp.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoesshopapp.model.data.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE accountId = :accountId")
    fun getUserByAccount(accountId: Int): LiveData<User>
}