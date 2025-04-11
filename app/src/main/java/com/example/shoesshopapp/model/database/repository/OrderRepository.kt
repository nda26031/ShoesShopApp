package com.example.shoesshopapp.model.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.data.OrderItem
import com.example.shoesshopapp.model.data.OrderStatus
import com.example.shoesshopapp.model.data.relationship.OrderWithOrderItem
import com.example.shoesshopapp.model.database.dao.CartDAO
import com.example.shoesshopapp.model.database.dao.CartItemDAO
import com.example.shoesshopapp.model.database.dao.OrderDAO
import com.example.shoesshopapp.model.database.dao.OrderItemDAO
import com.example.shoesshopapp.model.database.dao.ProductSizeDAO
import com.example.shoesshopapp.model.database.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(application: Application) {
    private var orderDao: OrderDAO
    private var cartDao: CartDAO
    private var cartItemDao: CartItemDAO
    private var orderItemDao: OrderItemDAO
    private var productSizeDao: ProductSizeDAO

    init {
        val database = AppDatabase.getDatabase(application)
        orderDao = database.getOrderDao()
        cartDao = database.getCartDao()
        cartItemDao = database.getCartItemDao()
        orderItemDao = database.getOrderItemDao()
        productSizeDao = database.getProductSizeDao()
    }

    suspend fun insertOrder(userId: Int, cartId: Int, totalCost: Double): Boolean {
        withContext(Dispatchers.IO) {

            val cartWithCartItems = cartDao.getCartWithCartItemsNoLiveData(cartId)
            val randomNum = (10000..99999).random()
            val orderCode = "ODC-$randomNum"

            val orderId = orderDao.insertOrder(
                Order(
                    userId = userId,
                    orderCode = orderCode,
                    orderStatus = OrderStatus.PENDING,
                    totalCost = totalCost
                )
            ).toInt()

            val orderItems = cartWithCartItems.cartItems.map {
                OrderItem(
                    orderId = orderId,
                    productId = it.product.productId,
                    productSizeId = it.productSize.productSizeId,
                    quantity = it.cartItem.quantity
                )
            }

            orderItemDao.insertOrderItems(orderItems)

            cartWithCartItems.cartItems.map {
                productSizeDao.updateProductSizeQuantity(
                    productSizeId = it.productSize.productSizeId,
                    quantity = it.cartItem.quantity
                )
            }

            cartDao.clearCartItems(cartId)
            return@withContext true
        }
        return false
    }

    fun getOrderByUserId(userId: Int): LiveData<List<Order>> =
        orderDao.getOrderByUserId(userId)

    fun getOrderWithOrderItem(orderId: Int): LiveData<OrderWithOrderItem> =
        orderDao.getOrderWithOrderItem(orderId)

    fun getAllOrder(): LiveData<List<Order>> = orderDao.getAllOrder()

    suspend fun updateOrder(order: Order) = withContext(Dispatchers.IO) {
        orderDao.updateOrder(order)
    }
}