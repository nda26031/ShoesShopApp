package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentDashboardBinding
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.ui.fragment.users.brand.BrandFragment
import com.example.shoesshopapp.ui.fragment.users.cart.CartFragment
import com.example.shoesshopapp.ui.fragment.users.product.ProductFragment
import com.example.shoesshopapp.ui.fragment.users.product.productDetail.ProductDetailFragment

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private var userId: Int = -1
    private var cartId: Int = -1

    private val officialBrandAdapter: OfficialBrandAdapter by lazy {
        OfficialBrandAdapter()
    }

    private val recommendationProductAdapter: RecommendationProductAdapter by lazy {
        RecommendationProductAdapter(onProductClick = { product ->
            onProductClick(product)
        })
    }

    private val dashboardViewModel: DashboardViewModel by lazy {
        ViewModelProvider(
            this,
            DashboardViewModel.DashboardModelFactory(requireActivity().application)
        )[DashboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            userId = it.getInt("userId")
            Log.d("Receive userId ", userId.toString())
        }
        setupNavigate()
        setupOfficialBrandRecyclerView()
        getAllBrands()
        setupRecommendationProductRecyclerView()
        getAllRecommendedProduct()
        getCart()
    }

    private fun setupNavigate() {
        binding.cvCart.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("cartId", cartId)
            bundle.putInt("userId", userId)
            findNavController().navigate(R.id.action_userHomeFragment_to_cartFragment, bundle)
        }
    }

    private fun onProductClick(product: Product) {
        val bundle = Bundle()
        bundle.putInt("productId", product.productId)
        bundle.putInt("userId", userId)
        findNavController().navigate(R.id.action_userHomeFragment_to_productDetailFragment, bundle)
    }

    private fun setupOfficialBrandRecyclerView() {
        binding.rvOfficialBrand.adapter = officialBrandAdapter
        binding.rvOfficialBrand.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getAllBrands() {
        dashboardViewModel.getAllBrands().observe(viewLifecycleOwner) {
            officialBrandAdapter.submitList(it)
        }
    }

    private fun getCart() {
        dashboardViewModel.getCartByUserId(userId).observe(viewLifecycleOwner) {
            cartId = it.cartId
        }
    }

    private fun setupRecommendationProductRecyclerView() {
        binding.rvRecommendationProduct.adapter = recommendationProductAdapter
        binding.rvRecommendationProduct.layoutManager = GridLayoutManager(context, 2)
    }

    private fun getAllRecommendedProduct() {
        dashboardViewModel.getAllRecommendedProduct().observe(viewLifecycleOwner) {
            recommendationProductAdapter.submitList(it)
        }
    }
}