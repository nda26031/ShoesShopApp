package com.example.shoesshopapp.ui.fragment.admin.brand.brandManger

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAddBrandBinding
import com.example.shoesshopapp.model.data.UIState
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandViewModel
import com.example.shoesshopapp.ui.fragment.register.RegisterViewModel

class AddBrandFragment : Fragment() {

    private lateinit var binding: FragmentAddBrandBinding

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
        binding = FragmentAddBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandNameFocusListener()

        binding.ivBack.setOnClickListener {
            replaceFragment(BrandManagerFragment())
        }

        binding.btnAddBrand.setOnClickListener {
            saveBrand()
        }

        binding.btnAddBrandLogo.setOnClickListener {
            pickImage()
        }

        brandViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                UIState.Loading -> {
                    // Show loading state if needed
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

    private fun saveBrand(){
        val brandName = binding.edtBrandName.text.toString().trim()
        selectedImageUri?.let { uri ->
            if (brandName.isNotEmpty()) {
                brandViewModel.insertBrand(brandName, uri)
                clear()
            } else {
                Toast.makeText(context, "Please enter brand name", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImage(){
        imagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

    private fun validBrandName(): String? {

        val brandNameText = binding.edtBrandName.text.toString()
        if (brandNameText.isNotEmpty()) {
            return "Add brand successfully"
        }
        return null
    }

    private fun clear(){
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