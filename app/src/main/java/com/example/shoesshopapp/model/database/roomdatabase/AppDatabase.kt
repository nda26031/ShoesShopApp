package com.example.shoesshopapp.model.database.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoesshopapp.model.data.Account
import com.example.shoesshopapp.model.data.Admin
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.Converters
import com.example.shoesshopapp.model.data.FavouriteProduct
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.data.User
import com.example.shoesshopapp.model.database.dao.AccountDAO
import com.example.shoesshopapp.model.database.dao.AdminDAO
import com.example.shoesshopapp.model.database.dao.BrandDAO
import com.example.shoesshopapp.model.database.dao.FavouriteProductDAO
import com.example.shoesshopapp.model.database.dao.ProductDAO
import com.example.shoesshopapp.model.database.dao.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Account::class,
        User::class,
        Admin::class,
        Brand::class,
        Product::class,
        ProductSize::class,
        FavouriteProduct::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDAO
    abstract fun getUserDao(): UserDAO
    abstract fun getAdminDao(): AdminDAO
    abstract fun getBrandDao(): BrandDAO
    abstract fun getProductDao(): ProductDAO
    abstract fun getFavouriteProductDao(): FavouriteProductDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database5"
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