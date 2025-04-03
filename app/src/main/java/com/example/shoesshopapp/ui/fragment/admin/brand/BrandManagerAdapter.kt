package com.example.shoesshopapp.ui.fragment.admin.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.BrandManagerItemLayoutBinding
import com.example.shoesshopapp.model.data.Brand

class BrandAdapter(
    private val onClickEdit: (Brand) -> Unit,
    private val onClickDelete: (Brand) -> Unit
) : ListAdapter<Brand, BrandAdapter.BrandViewHolder>(BrandDiffUtil()) {

    class BrandViewHolder(
        private val binding: BrandManagerItemLayoutBinding,
        private val onClickEdit: (Brand) -> Unit,
        private val onClickDelete: (Brand) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            binding.tvBrandName.text = brand.brandName
            brand.brandLogo.let {
                binding.imgBrand.setImageBitmap(it)
            }

            binding.imgEdit.setOnClickListener {
                onClickEdit.invoke(brand)
            }

            binding.imgDelete.setOnClickListener {
                onClickDelete.invoke(brand)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = BrandManagerItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BrandViewHolder(binding, onClickEdit, onClickDelete)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }
}

class BrandDiffUtil : DiffUtil.ItemCallback<Brand>() {
    override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem.brandId == newItem.brandId
    }

    override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
        return oldItem == newItem
    }

}
