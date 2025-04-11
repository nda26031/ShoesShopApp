package com.example.shoesshopapp.ui.fragment.admin.order.orderManagerDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshopapp.databinding.ItemProductOrderLayoutBinding
import com.example.shoesshopapp.model.data.relationship.OrderItemWithDetails

class OrderManagerDetailAdapter :
    ListAdapter<OrderItemWithDetails, OrderManagerDetailAdapter.OrderManagerDetailViewHolder>(
        OrderManagerDetailDiffUtil()
    ) {
    class OrderManagerDetailViewHolder(private val binding: ItemProductOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderManagerDetailViewHolder {
        val binding = ItemProductOrderLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderManagerDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderManagerDetailViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class OrderManagerDetailDiffUtil() : DiffUtil.ItemCallback<OrderItemWithDetails>() {
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