package com.example.shoesshopapp.ui.fragment.users.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemProductLayoutBinding
import com.example.shoesshopapp.model.data.Product

class ProductAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtil()) {
    class ProductViewHolder(
        private val binding: ItemProductLayoutBinding,
        private val onItemClick: (Product) -> Unit,
        private val onFavoriteClick: (Product) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.productName
                tvProductBrand.text = product.brandName
                tvProductPrice.text = String.format("%.0f", product.price)
                ivProduct.setImageBitmap(product.image)
            }

            binding.root.setOnClickListener {
                onItemClick(product)
            }

            binding.ivFavourite.setOnClickListener {
                onFavoriteClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
}