package com.example.shoesshopapp.ui.fragment.users.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentProductBinding
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.ui.fragment.users.product.productDetail.ProductDetailFragment


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(
            this,
            ProductViewModel.ProductViewModelFactory(requireActivity().application)
        )[ProductViewModel::class.java]
    }

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            onItemClick = { product ->
                onItemClick(product)
            },
            onAddToCartClick = { product ->
                onAddToCartClick(product)
            },
            onFavoriteClick = { product ->
                onFavoriteClick(product)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductAdapter()
        getAllProducts()
        setupSearchView()
    }

    private fun getAllProducts() {
        productViewModel.getAllProducts().observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
        }
    }

    private fun setupSearchView() {
        binding.svProduct.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchProducts(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchProducts(query)
                }
                return true
            }

        })
    }

    private fun searchProducts(searchQuery: String) {
        val query = "%$searchQuery%"
        productViewModel.searchProducts(query).observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }
    }

    private fun setupProductAdapter() {
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = LinearLayoutManager(context)
    }

    private fun onItemClick(product: Product) {
        replaceFragment(ProductDetailFragment())
    }

    private fun onAddToCartClick(product: Product) {
        Toast.makeText(
            requireContext(),
            "${product.productName} added to cart",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onFavoriteClick(product: Product) {
        Toast.makeText(
            requireContext(),
            "${product.productName} added to favorite",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }


}