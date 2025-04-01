package com.example.shoesshopapp.ui.fragment.admin.brand

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentBrandManagerBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.model.data.UIState
import com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.AddBrandFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.UpdateBrandFragment


class BrandManagerFragment : Fragment() {

    private lateinit var binding: FragmentBrandManagerBinding

    private val brandViewModel: BrandViewModel by lazy {
        ViewModelProvider(
            this,
            BrandViewModel.BrandViewModelFactory(requireActivity().application)
        )[BrandViewModel::class.java]
    }

    private val brandAdapter: BrandAdapter by lazy {
        BrandAdapter(
            onClickEdit = { brand -> onClickEdit(brand) },
            onClickDelete = { brand -> onClickDelete(brand) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBrandManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvBrand = binding.rvBrand
        rvBrand.adapter = brandAdapter
        rvBrand.layoutManager = LinearLayoutManager(context)

        brandViewModel.getAllBrands().observe(viewLifecycleOwner) {
            brandAdapter.submitList(it)
        }

        binding.fabAddBrand.setOnClickListener {
            replaceFragment(AddBrandFragment())
        }
        observeUiState()

    }

    private fun onClickEdit(brand: Brand) {
        val bundle = Bundle()
        bundle.putInt("brandId", brand.brandId)
        Log.d("BrandManagerFragment", "Put Brand ID: ${brand.brandId}")
        val updateBrandFragment = UpdateBrandFragment()
        updateBrandFragment.arguments = bundle
        replaceFragment(UpdateBrandFragment())
    }

    private fun onClickDelete(brand: Brand) {
        showDeleteConfirmation(brand)
    }

    private fun showDeleteConfirmation(brand: Brand) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Brand")
            .setMessage("Are you sure you want to delete ${brand.brandName} ?")
            .setPositiveButton("Delete") { _, _ ->
                brandViewModel.deleteBrandById(brand.brandId)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

    private fun observeUiState() {
        brandViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Error -> Toast.makeText(
                    requireContext(),
                    state.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()

                UIState.Loading -> {
                    // Show loading state if needed
                }
                is UIState.Success -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}