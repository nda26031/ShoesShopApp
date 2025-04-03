package com.example.shoesshopapp.ui.fragment.admin.product

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentProductManagerBinding
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.addProduct.AddProductFragment
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct.UpdateProductFragment
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.SizeManagerFragment


class ProductManagerFragment : Fragment() {

    private lateinit var binding: FragmentProductManagerBinding

    private val productManagerViewModel: ProductMangerViewModel by lazy {
        ViewModelProvider(
            this,
            ProductMangerViewModel.ProductViewModelFactory(requireActivity().application)
        )[ProductMangerViewModel::class.java]
    }

    private val productManagerAdapter: ProductManagerAdapter by lazy {
        ProductManagerAdapter(
            onClickEdit = { product -> onClickEdit(product) },
            onClickDelete = { product -> onClickDelete(product) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddProduct.setOnClickListener {
            replaceFragment(AddProductFragment())
        }
        setupAdapter()
        getAllBrand()
    }

    private fun setupAdapter() {
        binding.rvProduct.adapter = productManagerAdapter
        binding.rvProduct.layoutManager = LinearLayoutManager(context)
    }

    private fun getAllBrand() {
        productManagerViewModel.getAllBrands().observe(viewLifecycleOwner) { products ->
            productManagerAdapter.submitList(products)
        }
    }

    private fun onClickEdit(product: Product) {
        val bundle = Bundle()
        bundle.putInt("productId", product.productId)
        val updateProductFragment = UpdateProductFragment()
        updateProductFragment.arguments = bundle
        replaceFragment(updateProductFragment)
    }

    private fun onClickDelete(product: Product) {
        showDeleteConfirmation(product)
    }

    private fun showDeleteConfirmation(product: Product) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Brand")
            .setMessage("Are you sure you want to delete ${product.brandName} ?")
            .setPositiveButton("Delete") { _, _ ->
                productManagerViewModel.deleteBrand(product)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }
}