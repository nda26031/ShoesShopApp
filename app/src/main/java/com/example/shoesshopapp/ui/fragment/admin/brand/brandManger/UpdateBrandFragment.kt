package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentUpdateBrandBinding
import com.example.shoesshopapp.model.data.UIState
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.properties.Delegates

class UpdateBrandFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBrandBinding
    private val brandViewModel: BrandViewModel by lazy {
        ViewModelProvider(
            this,
            BrandViewModel.BrandViewModelFactory(requireActivity().application)
        )[BrandViewModel::class.java]
    }

    private var selectedImageUri: Uri? = null
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            Toast.makeText(context, "Image Selected", Toast.LENGTH_SHORT).show()
        }
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
        val brandId = arguments?.getInt("brandId")
        Log.d("UpdateBrandFragment", "Brand ID: $brandId")

        brandNameFocusListener()
        if (brandId != null) {
            getBrandById(brandId)
        }

        brandViewModel.currentBrand.observe(viewLifecycleOwner) { brand ->
            binding.edtBrandName.setText(brand?.brandName)
        }



        binding.ivBack.setOnClickListener {
            replaceFragment(BrandManagerFragment())
        }

        binding.btnUpdateBrandLogo.setOnClickListener {
            pickImage()
        }

        binding.btnUpdateBrand.setOnClickListener {
            if (brandId != null) {
                updateBrand(brandId)
            }
        }

        brandViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("UpdateBrandFragment", "Brand update failed")
                }

                UIState.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Loading...",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("UpdateBrandFragment", "Loading brand data")
                }

                is UIState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    replaceFragment(BrandManagerFragment())
                }
            }
        }

    }

    private fun getBrandById(brandId: Int) {
        brandViewModel.getBrandById(brandId)
    }

    private fun updateBrand(brandId: Int) {
        val brandName = binding.edtBrandName.text.toString().trim()

        // Validate inputs
        if (brandName.isEmpty()) {
            binding.edtBrandName.error = "Brand name is required"
            return
        }

        // Get current brand's image as fallback
        if (selectedImageUri == null) {
            brandViewModel.currentBrand.value?.let { brand ->
                // Create a temporary file to store the current logo
                try {
                    val tempFile =
                        File.createTempFile("current_logo", ".jpg", requireContext().cacheDir)
                    FileOutputStream(tempFile).use { stream ->
                        stream.write(brand.brandLogo)
                    }
                    selectedImageUri = Uri.fromFile(tempFile)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error preparing image: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            } ?: run {
                Toast.makeText(requireContext(), "Please select a logo image", Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }

        // Update brand
        brandViewModel.updateBrand(brandId, brandName, selectedImageUri!!)
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

    private fun pickImage() {
        imagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private fun validBrandName(): String? {

        val brandNameText = binding.edtBrandName.text.toString()
        if (brandNameText.isNotEmpty()) {
            return "Add brand successfully"
        }
        return null
    }

    private fun clear() {
        binding.edtBrandName.text?.clear()
        selectedImageUri = null
    }

    private fun brandNameFocusListener() {
        binding.edtBrandName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilBrandName.helperText = validBrandName()
            }
        }
    }
}