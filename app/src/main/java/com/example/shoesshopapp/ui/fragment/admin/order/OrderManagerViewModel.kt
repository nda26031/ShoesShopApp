package com.example.shoesshopapp.ui.fragment.admin.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.database.repository.OrderRepository
import com.example.shoesshopapp.ui.fragment.users.order.OrderViewModel

class OrderManagerViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrderRepository(application)

    fun getAllOrder(): LiveData<List<Order>> = orderRepository.getAllOrder()

    class OrderManagerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderManagerViewModel::class.java)) return OrderManagerViewModel(
                application
            ) as T
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}