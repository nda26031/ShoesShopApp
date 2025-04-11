package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.ItemSizeLayoutBinding
import com.example.shoesshopapp.model.data.ProductSize

class SizeAdapter(private val onSizeClick: (ProductSize) -> Unit) :
    ListAdapter<ProductSize, SizeAdapter.SizeViewHolder>(SizeDiffUtil()) {
    class SizeViewHolder(
        private val binding: ItemSizeLayoutBinding,
        private val onSizeClick: (ProductSize) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(productSize: ProductSize) {
            binding.tvSize.text = productSize.size
            binding.clSize.setBackgroundResource(
                if (productSize.isSelect) R.drawable.size_checked else R.drawable.size_unchecked
            )
            binding.root.setOnClickListener {
                onSizeClick.invoke(productSize)
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