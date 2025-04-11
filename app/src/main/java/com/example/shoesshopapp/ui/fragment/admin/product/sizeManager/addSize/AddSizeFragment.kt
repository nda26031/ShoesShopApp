package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.addSize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAddSizeBinding
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.SizeManagerFragment

class AddSizeFragment : Fragment() {

    private lateinit var binding: FragmentAddSizeBinding
    private var productId: Int = -1

    private val addSizeViewModel: AddSizeViewModel by lazy {
        ViewModelProvider(
            this,
            AddSizeViewModel.AddSizeViewModelFactory(requireActivity().application)
        )[AddSizeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productId = it.getInt("productId")
            Log.d("AddSizeFragment", "Product ID: $productId")
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(SizeManagerFragment())
        }

        binding.btnAddSize.setOnClickListener {
            insertProductSize()
        }
    }

    private fun insertProductSize() {
        val size = binding.edtSize.text.toString()
        val quantity = binding.edtQuantity.text.toString().toInt()

        if (size.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter product size", Toast.LENGTH_SHORT).show()
            return
        }

        if (quantity < 0) {
            Toast.makeText(
                requireContext(),
                "Please enter available product quantity",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val productSize = ProductSize(
            productId = productId,
            size = size,
            quantity = quantity
        )
        addSizeViewModel.insertProductSize(productSize)
        Toast.makeText(requireContext(), "Brand added successfully", Toast.LENGTH_SHORT).show()
        clearForm()

        replaceFragment(ProductManagerFragment())
    }

    private fun clearForm() {
        binding.edtSize.text?.clear()
        binding.edtQuantity.text?.clear()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}