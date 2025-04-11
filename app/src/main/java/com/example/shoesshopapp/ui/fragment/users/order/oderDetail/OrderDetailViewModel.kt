package com.example.shoesshopapp.ui.fragment.users.order.oderDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.User
import com.example.shoesshopapp.model.data.relationship.OrderWithOrderItem
import com.example.shoesshopapp.model.database.repository.OderItemRepository
import com.example.shoesshopapp.model.database.repository.OrderRepository
import com.example.shoesshopapp.model.database.repository.UserRepository
import com.example.shoesshopapp.ui.fragment.users.order.OrderViewModel

class OrderDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrderRepository(application)
    private val userRepository = UserRepository(application)

    fun getOrderWithOrderItem(orderId: Int): LiveData<OrderWithOrderItem> =
        orderRepository.getOrderWithOrderItem(orderId)

    fun getUserById(userId: Int): LiveData<User> =
        userRepository.getUserByUserId(userId)


    class OrderDetailViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) return OrderDetailViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}