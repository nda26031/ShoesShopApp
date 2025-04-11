package com.example.shoesshopapp.ui.fragment.users.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.data.relationship.CartWithCartItems
import com.example.shoesshopapp.model.database.repository.CartItemRepository
import com.example.shoesshopapp.model.database.repository.CartRepository
import com.example.shoesshopapp.model.database.repository.OrderRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepository = CartRepository(application)
    private val cartItemRepository = CartItemRepository(application)
    private val orderRepository = OrderRepository(application)

    private val _checkOutStatus = MutableLiveData<Boolean>(false)
    val checkOutStatus: LiveData<Boolean> = _checkOutStatus

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

    fun insertOrder(userId: Int, cartId: Int, totalCost: Double) {
        viewModelScope.launch {
            orderRepository.insertOrder(userId, cartId, totalCost)
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
