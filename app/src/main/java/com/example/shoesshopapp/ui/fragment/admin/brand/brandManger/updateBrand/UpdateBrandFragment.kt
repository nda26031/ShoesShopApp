package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.updateBrand

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentUpdateBrandBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel

class UpdateBrandFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBrandBinding
    private var selectedImageBitmap: Bitmap? = null
    private var brandId: Int = -1

    // Image picker launcher using activity result API
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val imageStream = requireContext().contentResolver.openInputStream(uri)
                selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
                binding.ivBrandLogo.setImageBitmap(selectedImageBitmap)
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), "Fail to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val updateBrandViewModel: UpdateBrandViewModel by lazy {
        ViewModelProvider(
            this,
            UpdateBrandViewModel.UpdateBrandViewModelFactory(requireActivity().application)
        )[UpdateBrandViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            brandId = it.getInt("brandId", -1)
            Log.d("UpdateBrandFragment", "Received brand ID: $brandId")
            if (brandId != -1) {
                getBrandById(brandId)
            } else {
                Toast.makeText(requireContext(), "Invalid brand ID", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(BrandManagerFragment())
        }

        binding.btnUpdateBrandLogo.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnUpdateBrand.setOnClickListener {
            updateBrand()
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun updateBrand() {
        val brandName = binding.edtBrandName.text.toString()
        if (brandName.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter brand name", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageBitmap == null) {
            Toast.makeText(requireContext(), "Please select brand logo", Toast.LENGTH_SHORT).show()
            return
        }

        val brand = Brand(
            brandId = brandId,
            brandName = brandName,
            brandLogo = selectedImageBitmap!!
        )

        updateBrandViewModel.updateBrand(brand)
        Log.d("UpdateBrandFragment", "Updated brand: $brand")
        Toast.makeText(requireContext(), "Brand updated successfully", Toast.LENGTH_SHORT).show()
        clearForm()

        replaceFragment(BrandManagerFragment())
    }

    private fun getBrandById(brandId: Int) {
        updateBrandViewModel.getBrandById(brandId).observe(viewLifecycleOwner) { brand ->
            binding.edtBrandName.setText(brand.brandName)
            brand.brandLogo.let {
                binding.ivBrandLogo.setImageBitmap(it)
                selectedImageBitmap = it
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }


    private fun clearForm() {
        binding.edtBrandName.text?.clear()
        selectedImageBitmap = null
    }

}