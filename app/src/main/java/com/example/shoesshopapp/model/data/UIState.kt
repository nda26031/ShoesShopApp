package com.example.shoesshopapp.model.data

sealed class UIState {
    data object Loading : UIState()
    data class Success(val message: String) : UIState()
    data class Error(val errorMessage: String) : UIState()
}