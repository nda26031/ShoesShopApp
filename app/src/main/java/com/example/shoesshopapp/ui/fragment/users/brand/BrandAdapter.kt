package com.example.shoesshopapp.ui.fragment.users.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemBrandLayoutBinding
import com.example.shoesshopapp.model.data.Brand

class BrandAdapter(private val onItemClick: (Brand) -> Unit) : ListAdapter<Brand, BrandAdapter.BrandViewHolder>(BrandDiffCallback()) {
    class BrandViewHolder(private val binding: ItemBrandLayoutBinding, private val onItemClick: (Brand) -> Unit) : ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            binding.tvBrandName.text = brand.brandName
            brand.brandLogo.let {
                binding.imgBrand.setImageBitmap(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = ItemBrandLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BrandViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class BrandDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<Brand>() {
        override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem.brandId == newItem.brandId
        }

        override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem == newItem
        }
    }
}