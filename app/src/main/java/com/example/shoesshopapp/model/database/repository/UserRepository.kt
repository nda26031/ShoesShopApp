package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.User
import com.example.shoesshopapp.model.database.dao.UserDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase

class UserRepository(application: Application) {
    private val userDao: UserDAO

    init {
        val database = AppDatabase.getDatabase(application)
        userDao = database.getUserDao()
    }

    fun getUserByAccount(accountId: Int):LiveData<User> = userDao.getUserByAccount(accountId)
}