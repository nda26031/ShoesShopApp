package com.example.shoesshopapp.ui.fragment.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.database.repository.UserRepository
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardViewModel

class UserHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    fun getUserByAccount(accountId: Int) = userRepository.getUserByAccount(accountId)

    class UserHomeViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserHomeViewModel::class.java)) return UserHomeViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}