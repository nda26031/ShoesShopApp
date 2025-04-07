package com.example.shoesshopapp.ui.fragment.users.cart.thankyou

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemProductCartLayoutBinding
import com.example.shoesshopapp.model.dataTest.CartProduct

class CartProductAdapter :
    ListAdapter<CartProduct, CartProductAdapter.CartProductViewHolder>(CartProductDiffUtil()) {
    class CartProductViewHolder(private val binding: ItemProductCartLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(cartProduct: CartProduct) {
            binding.tvProductName.text = cartProduct.name
            binding.tvProductPrice.text = cartProduct.price.toString()
            binding.tvQuantity.text = cartProduct.quantity.toString()
            binding.ivProductImage.setImageResource(cartProduct.imageUrl)
        }
    }

    class CartProductDiffUtil : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val binding = ItemProductCartLayoutBinding.inflate(
            parent.context.getSystemService(LayoutInflater::class.java),
            parent,
            false
        )
        return CartProductViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val cartProduct = getItem(position)
        holder.bind(cartProduct)
    }
}