package com.example.shoesshopapp.ui.fragment.users.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.ItemOrderManagerLayoutBinding
import com.example.shoesshopapp.model.data.Order

class OrderAdapter(private val onItemClick: (Order) -> Unit) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {
    class OrderViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderManagerLayoutBinding.inflate(
            parent.context.getSystemService(LayoutInflater::class.java),
            parent,
            false
        )
        return OrderViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    class OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }
}