package com.example.shoesshopapp.ui.fragment.admin.brand

import android.graphics.BitmapFactory
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

    inner class BrandViewHolder(
        private val binding: BrandManagerItemLayoutBinding,
        private val onClickEdit: (Brand) -> Unit,
        private val onClickDelete: (Brand) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            val bitmap =
                BitmapFactory.decodeByteArray(brand.brandLogo, 0, brand.brandLogo.size)
            binding.imgBrand.setImageBitmap(bitmap)
            binding.tvBrandName.text = brand.brandName

            binding.imgEdit.setOnClickListener {
                onClickEdit(brand)
            }

            binding.imgDelete.setOnClickListener {
                onClickDelete(brand)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = BrandManagerItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
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
