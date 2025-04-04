package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemRecommendedProductLayoutBinding
import com.example.shoesshopapp.model.data.Product

class RecommendationProductAdapter :
    ListAdapter<Product, RecommendationProductAdapter.RecommendationProductViewHolder>(
        RecommendationProductDiffUtil()
    ) {
    class RecommendationProductViewHolder(private val binding: ItemRecommendedProductLayoutBinding) :
        ViewHolder(binding.root) {

        fun bind(product: Product) {
            product.image.let {
                binding.ivProduct.setImageBitmap(it)
            }
            binding.tvProductName.text = product.productName
            binding.tvProductPrice.text = product.price.toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationProductViewHolder {
        val binding = ItemRecommendedProductLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendationProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationProductViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class RecommendationProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
}