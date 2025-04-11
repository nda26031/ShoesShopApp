package com.example.shoesshopapp.ui.fragment.users.brand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentBrandBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel
import com.example.shoesshopapp.ui.fragment.users.brand.brandWithProducts.BrandWithProductsFragment

class BrandFragment : Fragment() {
    private lateinit var binding: FragmentBrandBinding

    private val brandViewModel: BrandViewModel by lazy {
        ViewModelProvider(
            this,
            BrandViewModel.BrandViewModelFactory(requireActivity().application)
        )[BrandViewModel::class.java]
    }

    private val brandAdapter: BrandAdapter by lazy {
        BrandAdapter(onItemClick = { brand -> onItemClick(brand) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBrandAdapter()
        getAllBrand()
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.svBrand.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchBrands(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchBrands(query)
                }
                return true
            }
        })
    }


    private fun searchBrands(searchQuery: String) {
        val query = "%$searchQuery%"
        brandViewModel.searchBrands(query).observe(viewLifecycleOwner) {
            brandAdapter.submitList(it)
        }
    }

    private fun setupBrandAdapter() {
        binding.rvBrand.adapter = brandAdapter
        binding.rvBrand.layoutManager = LinearLayoutManager(context)
    }

    private fun onItemClick(brand: Brand) {

        replaceFragment(BrandWithProductsFragment())
    }

    private fun getAllBrand() {
        brandViewModel.getAllBrands().observe(viewLifecycleOwner) { brands ->
            brandAdapter.submitList(brands)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}