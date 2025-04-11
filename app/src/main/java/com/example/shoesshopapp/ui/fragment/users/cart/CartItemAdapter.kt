package com.example.shoesshopapp.ui.fragment.users.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemProductCartLayoutBinding
import com.example.shoesshopapp.model.data.relationship.CartItemWithDetails

class CartItemAdapter(
    private val onQuantityIncrease: (CartItemWithDetails) -> Unit,
    private val onQuantityDecrease: (CartItemWithDetails) -> Unit,
    private val onDeleteClick: (CartItemWithDetails) -> Unit
) :
    ListAdapter<CartItemWithDetails, CartItemAdapter.CartItemWithDetailsViewHolder>(
        CartItemWithDetailsDiffUtil()
    ) {
    class CartItemWithDetailsViewHolder(
        private val binding: ItemProductCartLayoutBinding,
        private val onQuantityIncrease: (CartItemWithDetails) -> Unit,
        private val onQuantityDecrease: (CartItemWithDetails) -> Unit,
        private val onDeleteClick: (CartItemWithDetails) -> Unit
    ) :
        ViewHolder(binding.root) {

        fun bind(cartItemWithDetails: CartItemWithDetails) {
            binding.tvProductName.text = cartItemWithDetails.product.productName
            binding.tvProductSizeValue.text = cartItemWithDetails.productSize.size
            binding.tvQuantity.text = cartItemWithDetails.cartItem.quantity.toString()
            binding.tvProductPrice.text = String.format("%.0f", cartItemWithDetails.product.price)
            binding.ivProductImage.setImageBitmap(cartItemWithDetails.product.image)

            binding.tvIncrease.setOnClickListener {
                onQuantityIncrease(cartItemWithDetails)
            }

            binding.tvDecrease.setOnClickListener {
                onQuantityDecrease(cartItemWithDetails)
            }

            binding.ivDelete.setOnClickListener {
                onDeleteClick(cartItemWithDetails)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartItemWithDetailsViewHolder {
        val binding = ItemProductCartLayoutBinding.inflate(
            parent.context.getSystemService(LayoutInflater::class.java),
            parent,
            false
        )
        return CartItemWithDetailsViewHolder(
            binding,
            onQuantityIncrease,
            onQuantityDecrease,
            onDeleteClick
        )

    }

    override fun onBindViewHolder(holder: CartItemWithDetailsViewHolder, position: Int) {
        val cartItemWithDetails = getItem(position)
        holder.bind(cartItemWithDetails)
    }

    class CartItemWithDetailsDiffUtil : DiffUtil.ItemCallback<CartItemWithDetails>() {
        override fun areItemsTheSame(
            oldItem: CartItemWithDetails,
            newItem: CartItemWithDetails
        ): Boolean {
            return oldItem.cartItem.cartId == newItem.cartItem.cartId
        }

        override fun areContentsTheSame(
            oldItem: CartItemWithDetails,
            newItem: CartItemWithDetails
        ): Boolean {
            return oldItem == newItem
        }
    }
}