package com.example.shoesshopapp.model.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sPref: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(username: String, role:String){
        val editor = sPref.edit()
        editor.putString("username", username)
        editor.putString("role", role)
        editor.putBoolean("isLoggedIn",true)
        editor.apply()
    }

    fun getUserRole(): String? {
        return sPref.getString("role", "user")
    }

    fun isLoggedIn(): Boolean {
        return sPref.getBoolean("isLoggedIn", false)
    }

    fun logout() {
        val editor = sPref.edit()
        editor.clear()
        editor.apply()
    }

}