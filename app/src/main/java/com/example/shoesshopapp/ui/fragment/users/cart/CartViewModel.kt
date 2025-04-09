package com.example.shoesshopapp.ui.fragment.users.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.data.relationship.CartWithCartItems
import com.example.shoesshopapp.model.database.repository.CartItemRepository
import com.example.shoesshopapp.model.database.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepository = CartRepository(application)
    private val cartItemRepository = CartItemRepository(application)

    fun getCartWithCartItems(cartId: Int): LiveData<CartWithCartItems> =
        cartRepository.getCartWithCartItems(cartId)

    fun updateCartItemQuantity(cartItemId: Int, quantity: Int) {
        viewModelScope.launch {
            cartItemRepository.updateCartItemQuantity(cartItemId, quantity)
        }
    }

    fun updateCartItemQuantity2(cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.updateCartItemQuantity2(cartItem)
        }
    }

    fun deleteCartItemById(cartItemId: Int) {
        viewModelScope.launch {
            cartItemRepository.deleteCartItemById(cartItemId)
        }
    }

    class CartViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                return CartViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
