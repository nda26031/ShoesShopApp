package com.example.shoesshopapp.ui.fragment.users.order.oderDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemProductOrderLayoutBinding
import com.example.shoesshopapp.model.data.relationship.OrderItemWithDetails

class OrderDetailAdapter :
    ListAdapter<OrderItemWithDetails, OrderDetailAdapter.OrderDetailViewHolder>(OrderDetailDiffUtil()) {
    class OrderDetailViewHolder(private val binding: ItemProductOrderLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(orderItemWithDetails: OrderItemWithDetails) {
            val productTotal =
                orderItemWithDetails.product.price * orderItemWithDetails.orderItem.quantity

            binding.tvProductName.text = orderItemWithDetails.product.productName
            binding.tvProductSizeValue.text = orderItemWithDetails.productSize.size
            binding.tvQuantity.text = "x ${orderItemWithDetails.orderItem.quantity}"
            binding.tvProductPrice.text = String.format("%.0f", productTotal)
            binding.ivProductImage.setImageBitmap(orderItemWithDetails.product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val binding = ItemProductOrderLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class OrderDetailDiffUtil() : DiffUtil.ItemCallback<OrderItemWithDetails>() {
        override fun areItemsTheSame(
            oldItem: OrderItemWithDetails,
            newItem: OrderItemWithDetails
        ): Boolean {
            return oldItem.orderItem.orderItemId == newItem.orderItem.orderItemId
        }

        override fun areContentsTheSame(
            oldItem: OrderItemWithDetails,
            newItem: OrderItemWithDetails
        ): Boolean {
            return oldItem == newItem
        }
    }
}