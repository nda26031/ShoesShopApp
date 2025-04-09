package com.example.shoesshopapp.ui.fragment.users.product.productDetail

import android.database.sqlite.SQLiteConstraintException
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
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.data.ProductSize
import com.example.shoesshopapp.model.data.relationship.ProductWithSizes
import com.example.shoesshopapp.ui.fragment.users.cart.CartFragment
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardFragment
import com.example.shoesshopapp.ui.fragment.users.product.ProductFragment

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private var productId: Int = -1
    private var userId: Int = -1
    private var productSizeId: Int = -1

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
            userId = it.getInt("userId")
        }
        getProductWithSizes(productId)
        setupSizeAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddToCart.setOnClickListener {
            addToCart()
        }
    }

    private fun addToCart() {
        try {
            productDetailViewModel.getCartByUserId(userId).observe(viewLifecycleOwner) { cart ->
                if (cart != null) {
                    val cartId = cart.cartId

                    val cartItem = CartItem(
                        cartId = cartId,
                        productId = productId,
                        productSizeId = productSizeId,
                        quantity = 1
                    )
                    productDetailViewModel.insertCartItem(cartItem)
                    Toast.makeText(
                        requireContext(),
                        "Product added to cart successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "Dont have cart", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            productDetailViewModel.deselectAllSizesForProduct(productId)

        } catch (e: SQLiteConstraintException) {
            Toast.makeText(requireContext(), "Please choose size", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getProductWithSizes(productId: Int) {
        productDetailViewModel.getProductWithAvailableSizes(productId)
            .observe(viewLifecycleOwner) { productWithSizes ->
                binding.tvProductName.text = productWithSizes.product.productName
                binding.tvProductPrice.text = String.format("%.0f", productWithSizes.product.price)
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
        productSizeId = productSize.productSizeId
    }

}
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentTransaction = parentFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.clUserHome, fragment)
//        fragmentTransaction.commit()
//    }

