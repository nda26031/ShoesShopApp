package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger.addBrand

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAddBrandBinding
import com.example.shoesshopapp.model.data.Brand
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerViewModel

class AddBrandFragment : Fragment() {

    private lateinit var binding: FragmentAddBrandBinding
    private var selectedImageBitmap: Bitmap? = null

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
                Log.e("AddBrandFragment", "Error loading image", e)
            }
        }
    }

    private val addBrandViewModel: AddBrandViewModel by lazy {
        ViewModelProvider(
            this,
            AddBrandViewModel.AddBrandViewModelFactory(requireActivity().application)
        )[AddBrandViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            replaceFragment(BrandManagerFragment())
        }

        binding.btnAddBrandLogo.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnAddBrand.setOnClickListener {
            insertBrand()
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun insertBrand() {
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
            brandName = brandName,
            brandLogo = selectedImageBitmap!!
        )

        addBrandViewModel.insertBrand(brand)
        Toast.makeText(requireContext(), "Brand added successfully", Toast.LENGTH_SHORT).show()
        clearForm()

        replaceFragment(BrandManagerFragment())

    }

    private fun clearForm() {
        binding.edtBrandName.text?.clear()
        binding.ivBrandLogo.setImageResource(R.drawable.ic_launcher_background)
        selectedImageBitmap = null
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }
}