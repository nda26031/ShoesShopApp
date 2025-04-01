package com.example.shoesshopapp.ui.fragment.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.Account
import com.example.shoesshopapp.model.database.repository.AccountRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : ViewModel() {

    private val repository = AccountRepository(application)

    private val _account = MutableLiveData<Account?>()
    val account: LiveData<Account?> = _account

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            if (result != null) {
                _account.value = result
            }
        }
    }

    class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) return LoginViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}