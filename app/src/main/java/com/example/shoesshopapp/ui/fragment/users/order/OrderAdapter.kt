package com.example.shoesshopapp.ui.fragment.users.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemOrderManagerLayoutBinding
import com.example.shoesshopapp.model.dataTest.Order

class OrderAdapter:ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {
    class OrderViewHolder(private val binding: ItemOrderManagerLayoutBinding) :ViewHolder(binding.root){
        fun bind(order: Order) {
            binding.ivProduct.setImageResource(order.imageUrl)
            binding.tvOrderCode.text = order.orderCode
            binding.tvUserName.text = order.userName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderManagerLayoutBinding.inflate(
            parent.context.getSystemService(LayoutInflater::class.java),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    class OrderDiffUtil:DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }
}