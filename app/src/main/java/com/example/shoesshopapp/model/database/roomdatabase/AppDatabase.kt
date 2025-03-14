package com.example.shoesshopapp.model.database.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoesshopapp.model.data.Account
import com.example.shoesshopapp.model.data.Admin
import com.example.shoesshopapp.model.data.User
import com.example.shoesshopapp.model.database.dao.AccountDAO
import com.example.shoesshopapp.model.database.dao.AdminDAO
import com.example.shoesshopapp.model.database.dao.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Account::class, User::class, Admin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDAO
    abstract fun getUserDao(): UserDAO
    abstract fun getAdminDao(): AdminDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database3"
                ).addCallback(roomDatabaseCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let {
                        val accountDao = it.getAccountDao()
                        val adminDao = it.getAdminDao()

                        val adminAccountId = accountDao.insertAccount(
                            Account(
                                username = "admin",
                                email = "admin@admin.com",
                                password = "admin",
                                role = "admin"
                            )
                        ).toInt()

                        adminDao.insertAdmin(
                            Admin(
                                accountId = adminAccountId,
                                adminLevel = "admin"
                            )
                        )
                    }
                }
            }
        }
    }
}