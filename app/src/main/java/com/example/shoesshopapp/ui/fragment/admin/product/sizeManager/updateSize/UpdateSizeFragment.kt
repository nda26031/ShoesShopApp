package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.updateSize

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentUpdateSizeBinding
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.SizeManagerFragment

class UpdateSizeFragment : Fragment() {

    private lateinit var binding: FragmentUpdateSizeBinding
    private var productSizeId: Int = -1
    private var productId: Int = -1

    private val updateSizeViewModel: UpdateSizeViewModel by lazy {
        ViewModelProvider(
            this,
            UpdateSizeViewModel.UpdateSizeViewModelFactory(requireActivity().application)
        )[UpdateSizeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productSizeId = it.getInt("productSizeId")
            productId = it.getInt("productId")
            Log.d("UpdateSizeFragment", "Product Size ID: $productSizeId")
            getProductSizeById(productSizeId)
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }

        binding.btnUpdateSize.setOnClickListener {
            updateSize()
        }
    }

    private fun updateSize() {
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
            productSizeId = productSizeId,
            productId = productId,
            size = size,
            quantity = quantity)

        updateSizeViewModel.updateProductSize(productSize)
        Toast.makeText(requireContext(), "Size updated successfully", Toast.LENGTH_SHORT).show()
        clearForm()

        replaceFragment(ProductManagerFragment())
    }

    private fun getProductSizeById(productSizeId: Int) {
        updateSizeViewModel.getProductSizeById(productSizeId).observe(viewLifecycleOwner) {
            binding.edtSize.setText(it.size)
            binding.edtQuantity.setText(it.quantity.toString())
        }
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