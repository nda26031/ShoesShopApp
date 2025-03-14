package com.example.shoesshopapp.ui.fragment.register

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Account
import com.example.shoesshopapp.model.database.repository.AccountRepository
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : ViewModel() {
    private val repository = AccountRepository(application)

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun registerAccount(
        username: String,
        email: String,
        password: String,
        fullName: String,
        address: String,
        phone: String
    ) {
        // Thực hiện đăng ký tài khoản và lưu thông tin người dùng vào cơ sở dữ liệu
        viewModelScope.launch {

            val isRegister =
                repository.registerAccount(username, email, password, fullName, address, phone)
            // Thực hiện các thao tác lưu trữ dữ liệu
            _registerResult.value = isRegister
        }
    }

    class RegisterViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) return RegisterViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}