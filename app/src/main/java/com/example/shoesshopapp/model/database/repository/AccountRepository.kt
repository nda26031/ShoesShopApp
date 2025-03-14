package com.example.shoesshopapp.model.database.repository

import android.app.Application
import com.example.shoesshopapp.model.data.Account
import com.example.shoesshopapp.model.data.User
import com.example.shoesshopapp.model.database.dao.AccountDAO
import com.example.shoesshopapp.model.database.dao.UserDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository(
    application: Application
) {
    private var accountDao: AccountDAO
    private var userDao: UserDAO

    init {
        val database = AppDatabase.getDatabase(application)
        accountDao = database.getAccountDao()
        userDao = database.getUserDao()
    }

    suspend fun registerAccount(
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        phone: String
    ): Boolean {
        return withContext(Dispatchers.IO) {

            val emailExist = accountDao.isEmailExist(email) > 0

            if (!emailExist) {
                val accountId = accountDao.insertAccount(
                    Account(
                        username = username,
                        email = email,
                        password = password,
                        role = "user"
                    )
                ).toInt()
                // Lưu thông tin người dùng vào bảng User
                userDao.insertUser(
                    User(
                        accountId = accountId,
                        fullName = fullName,
                        address = address,
                        phone = phone
                    )
                )
                return@withContext true // Registration successful
            } else {
                return@withContext false // Email already exists
            }
        }
    }

    suspend fun login(email: String, password: String): Account? {
        return withContext(Dispatchers.IO) {
            accountDao.getAccount(email, password)
        }
    }
}