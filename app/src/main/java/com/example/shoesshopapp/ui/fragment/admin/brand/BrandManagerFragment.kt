package com.example.shoesshopapp.ui.fragment.admin.brand

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentBrandManagerBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.addBrand.AddBrandFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.updateBrand.UpdateBrandFragment


class BrandManagerFragment : Fragment() {

    private lateinit var binding: FragmentBrandManagerBinding

    private val brandManagerViewModel: BrandManagerViewModel by lazy {
        ViewModelProvider(
            this,
            BrandManagerViewModel.BrandViewModelFactory(requireActivity().application)
        )[BrandManagerViewModel::class.java]
    }

    private val brandAdapter: BrandManagerAdapter by lazy {
        BrandManagerAdapter(
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

        binding.fabAddBrand.setOnClickListener {
            replaceFragment(AddBrandFragment())
        }

        setupAdapter()
        getAllBrand()

    }

    private fun setupAdapter() {
        binding.rvBrand.adapter = brandAdapter
        binding.rvBrand.layoutManager = LinearLayoutManager(context)
    }

    private fun getAllBrand() {
        brandManagerViewModel.getAllBrands().observe(viewLifecycleOwner) { brands ->
            brandAdapter.submitList(brands)
        }
    }

    private fun onClickEdit(brand: Brand) {
        val bundle = Bundle()
        bundle.putInt("brandId", brand.brandId)
        Log.d("BrandManagerFragment", "Put Brand ID: ${brand.brandId}")
        val updateBrandFragment = UpdateBrandFragment()
        updateBrandFragment.arguments = bundle
        replaceFragment(updateBrandFragment)
    }

    private fun onClickDelete(brand: Brand) {
        showDeleteConfirmation(brand)
    }

    private fun showDeleteConfirmation(brand: Brand) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Brand")
            .setMessage("Are you sure you want to delete ${brand.brandName} ?")
            .setPositiveButton("Delete") { _, _ ->
                brandManagerViewModel.deleteBrand(brand)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }


}