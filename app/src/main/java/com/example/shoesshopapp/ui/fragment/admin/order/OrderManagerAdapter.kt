package com.example.shoesshopapp.ui.fragment.admin.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.ItemOrderManagerLayoutBinding
import com.example.shoesshopapp.model.data.Order

class OrderManagerAdapter(private val onItemClick: (Order) -> Unit) :
    ListAdapter<Order, OrderManagerAdapter.OrderManagerViewHolder>(OrderManagerDiffUtil()) {
    class OrderManagerViewHolder(
        private val binding: ItemOrderManagerLayoutBinding,
        private val onItemClick: (Order) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.ivProduct.setImageResource(R.drawable.icon_order)
            binding.tvOrderCode.text = order.orderCode
            binding.tvStatus.text = order.orderStatus.toString()

            binding.root.setOnClickListener {
                onItemClick.invoke(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderManagerViewHolder {
        val binding = ItemOrderManagerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderManagerViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: OrderManagerViewHolder, position: Int) {
        val currentOrder = currentList[position]
        holder.bind(currentOrder)
    }

    class OrderManagerDiffUtil() : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }
}