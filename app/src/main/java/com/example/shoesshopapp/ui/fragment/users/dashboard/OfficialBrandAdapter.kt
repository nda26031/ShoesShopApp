package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemBrandDashboardLayoutBinding
import com.example.shoesshopapp.model.data.Brand

class OfficialBrandAdapter :
    ListAdapter<Brand, OfficialBrandAdapter.OfficialBrandViewHolder>(OfficialBrandDiffUtil()) {
    class OfficialBrandViewHolder(private val binding: ItemBrandDashboardLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            brand.brandLogo.let {
                binding.ivBrandLogo.setImageBitmap(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficialBrandViewHolder {
        val binding = ItemBrandDashboardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OfficialBrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfficialBrandViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class OfficialBrandDiffUtil : DiffUtil.ItemCallback<Brand>() {
        override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem.brandId == newItem.brandId
        }

        override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem == newItem
        }

    }
}