package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentSizeManagerBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct.UpdateProductViewModel
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.addSize.AddSizeFragment
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.updateSize.UpdateSizeFragment

class SizeManagerFragment : Fragment() {

    private lateinit var binding: FragmentSizeManagerBinding
    private var productId: Int = -1

    private val sizeManagerViewModel: SizeManagerViewModel by lazy {
        ViewModelProvider(
            this,
            SizeManagerViewModel.SizeManagerViewModelFactory(requireActivity().application)
        )[SizeManagerViewModel::class.java]
    }

    private val sizeManagerAdapter: SizeManagerAdapter by lazy {
        SizeManagerAdapter(
            onEditClick = { productSize -> onEditClick(productSize) },
            onDeleteClick = { productSize -> onDeleteClick(productSize) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSizeManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId")
            if (productId != -1) {
                getProductById(productId)
            } else {
                Toast.makeText(requireContext(), "Invalid brand ID", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabAddSize.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("productId", productId)
            val addSizeFragment = AddSizeFragment()
            addSizeFragment.arguments = bundle
            replaceFragment(addSizeFragment)
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }
        setupAdapter()
        getProductSizeByProductId(productId)
    }

    private fun onEditClick(productSize: ProductSize) {
        val bundle = Bundle()
        bundle.putInt("productSizeId", productSize.productSizeId)
        bundle.putInt("productId", productId)
        val updateSizeFragment = UpdateSizeFragment()
        updateSizeFragment.arguments = bundle
        replaceFragment(updateSizeFragment)
    }

    private fun onDeleteClick(productSize: ProductSize) {
        showDeleteConfirmation(productSize)
    }

    private fun showDeleteConfirmation(productSize: ProductSize) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Brand")
            .setMessage("Are you sure you want to delete size ${productSize.size} ?")
            .setPositiveButton("Delete") { _, _ ->
                sizeManagerViewModel.deleteProductSize(productSize)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupAdapter() {
        binding.rvSize.adapter = sizeManagerAdapter
        binding.rvSize.layoutManager = LinearLayoutManager(context)
    }

    private fun getProductSizeByProductId(productId: Int){
        sizeManagerViewModel.getProductSizeByProductId(productId).observe(viewLifecycleOwner) {
            sizeManagerAdapter.submitList(it)
        }
    }

    private fun getProductById(productId: Int) {
        sizeManagerViewModel.getProductById(productId).observe(viewLifecycleOwner) { product ->
            binding.tvTitleProductName.text = product.productName
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}