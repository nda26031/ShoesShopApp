package com.example.shoesshopapp.ui.fragment.users.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.database.repository.OrderRepository
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardViewModel

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrderRepository(application)

    fun getOrderByUserId(userId: Int): LiveData<List<Order>> =
        orderRepository.getOrderByUserId(userId)

    class OrderViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderViewModel::class.java)) return OrderViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}