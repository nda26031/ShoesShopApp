package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentProductDetailBinding
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.data.relationship.ProductWithSizes

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private var productId: Int = -1

    private val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(
            this,
            ProductDetailViewModel.ProductDetailViewModelFactory(requireActivity().application)
        )[ProductDetailViewModel::class.java]
    }

    private val sizeAdapter: SizeAdapter by lazy {
        SizeAdapter(onSizeClick = { productSize ->
            onSizeClick(productSize)
        }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId")
        }
        getProductWithSizes(productId)
        setupSizeAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_userHomeFragment)
        }
    }

    private fun getProductWithSizes(productId: Int) {
        productDetailViewModel.getProductWithAvailableSizes(productId)
            .observe(viewLifecycleOwner) { productWithSizes ->
                binding.tvProductName.text = productWithSizes.product.productName
                binding.tvProductPrice.text = productWithSizes.product.price.toString()
                binding.ivProduct.setImageBitmap(productWithSizes.product.image)
                binding.tvProductDescription.text = productWithSizes.product.description

                sizeAdapter.submitList(productWithSizes.sizes)
            }
    }

    private fun setupSizeAdapter() {
        binding.rvSize.adapter = sizeAdapter
        binding.rvSize.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun onSizeClick(productSize: ProductSize) {
        productDetailViewModel.selectSingleProductSize(productSize)
        Log.d("ProductDetailFragment", "Selected size: ${productSize.size}")
        Log.d(
            "ProductDetailFragment",
            "Selected size ID: ${productSize.productSizeId}"
        )
    }

}