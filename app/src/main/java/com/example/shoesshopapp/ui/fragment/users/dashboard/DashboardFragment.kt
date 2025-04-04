package com.example.shoesshopapp.ui.fragment.users.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentDashboardBinding
import com.example.shoesshopapp.ui.fragment.users.brand.BrandFragment
import com.example.shoesshopapp.ui.fragment.users.cart.CartFragment
import com.example.shoesshopapp.ui.fragment.users.product.ProductFragment

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding


    private val officialBrandAdapter: OfficialBrandAdapter by lazy {
        OfficialBrandAdapter()
    }

    private val recommendationProductAdapter: RecommendationProductAdapter by lazy {
        RecommendationProductAdapter()
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

        setupNavigate()
        setupOfficialBrandRecyclerView()
        getAllBrands()
        setupRecommendationProductRecyclerView()
        getAllRecommendedProduct()
    }

    private fun setupNavigate() {
        binding.cvCart.setOnClickListener {
            replaceFragment(CartFragment())
        }

        binding.tvSeeAllBrand.setOnClickListener {
            replaceFragment(BrandFragment())
        }

        binding.tvSeeAllRecommendationProduct.setOnClickListener {
            replaceFragment(ProductFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
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