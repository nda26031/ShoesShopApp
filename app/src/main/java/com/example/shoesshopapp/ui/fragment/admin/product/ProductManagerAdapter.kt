package com.example.shoesshopapp.ui.fragment.admin.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoesshopapp.databinding.ItemProductManagerLayoutBinding
import com.example.shoesshopapp.model.data.Product

class ProductManagerAdapter(
    private val onClickItem: (Product) -> Unit,
    private val onClickEdit: (Product) -> Unit,
    private val onClickDelete: (Product) -> Unit,
) :
    ListAdapter<Product, ProductManagerAdapter.ProductMangerViewHolder>(ProductManagerDiffUtil()) {
    class ProductMangerViewHolder(
        private val binding: ItemProductManagerLayoutBinding,
        private val onClickItem: (Product) -> Unit,
        private val onClickEdit: (Product) -> Unit,
        private val onClickDelete: (Product) -> Unit,
    ) :
        ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.productName
            binding.tvProductBrand.text = product.brandName
            binding.tvProductPrice.text = product.price.toString()
            product.image.let {
                binding.ivProduct.setImageBitmap(it)
            }

            binding.btnUpdate.setOnClickListener {
                onClickEdit.invoke(product)
            }

            binding.btnDelete.setOnClickListener {
                onClickDelete.invoke(product)
            }

            binding.root.setOnClickListener {
                onClickItem.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductMangerViewHolder {
        val binding = ItemProductManagerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductMangerViewHolder(binding, onClickItem, onClickEdit, onClickDelete)
    }

    override fun onBindViewHolder(holder: ProductMangerViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    class ProductManagerDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}