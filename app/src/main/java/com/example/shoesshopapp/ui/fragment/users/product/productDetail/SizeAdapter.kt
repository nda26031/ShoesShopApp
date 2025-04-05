package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemSizeLayoutBinding
import com.example.shoesshopapp.model.data.ProductSize

class SizeAdapter(private val onSizeClick: (ProductSize) -> Unit) :
    ListAdapter<ProductSize, SizeAdapter.SizeViewHolder>(SizeDiffUtil()) {
    class SizeViewHolder(
        private val binding: ItemSizeLayoutBinding,
        private val onSizeClick: (ProductSize) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(productSizes: ProductSize) {
            binding.tvSize.text = productSizes.size

            binding.root.setOnClickListener {
                onSizeClick(productSizes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val binding =
            ItemSizeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeViewHolder(binding, onSizeClick)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class SizeDiffUtil : DiffUtil.ItemCallback<ProductSize>() {
        override fun areItemsTheSame(
            oldItem: ProductSize,
            newItem: ProductSize
        ): Boolean {
            return oldItem.size == newItem.size
        }

        override fun areContentsTheSame(
            oldItem: ProductSize,
            newItem: ProductSize
        ): Boolean {
            return oldItem == newItem

        }

    }
}