package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemSizeManagerLayoutBinding
import com.example.shoesshopapp.model.data.ProductSize

class SizeManagerAdapter(
    private val onEditClick: (ProductSize) -> Unit,
    private val onDeleteClick: (ProductSize) -> Unit
) :
    ListAdapter<ProductSize, SizeManagerAdapter.SizeManagerViewHolder>(SizeManagerDiffUtil()) {
    class SizeManagerViewHolder(
        private val binding: ItemSizeManagerLayoutBinding,
        private val onEditClick: (ProductSize) -> Unit,
        private val onDeleteClick: (ProductSize) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(productSize: ProductSize) {
            binding.tvSize.text = productSize.size
            binding.tvQuantity.text = productSize.quantity.toString()

            binding.imgEdit.setOnClickListener {
                onEditClick.invoke(productSize)
            }

            binding.imgDelete.setOnClickListener {
                onDeleteClick.invoke(productSize)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeManagerViewHolder {
        val binding =
            ItemSizeManagerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeManagerViewHolder(binding, onEditClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: SizeManagerViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class SizeManagerDiffUtil : DiffUtil.ItemCallback<ProductSize>() {
        override fun areItemsTheSame(oldItem: ProductSize, newItem: ProductSize): Boolean {
            return oldItem.size == newItem.size
        }

        override fun areContentsTheSame(oldItem: ProductSize, newItem: ProductSize): Boolean {
            return oldItem == newItem
        }
    }
}