package com.example.shoesshopapp.ui.fragment.admin.product.productManager.updateProduct

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentUpdateProductBinding
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.productManager.addProduct.AddProductViewModel

class UpdateProductFragment : Fragment() {
    private lateinit var binding: FragmentUpdateProductBinding

    private var selectedImageBitmap: Bitmap? = null
    private var brandId: Int = -1
    private var productId: Int = -1
    private var brandName: String = ""

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val imageStream = requireContext().contentResolver.openInputStream(uri)
                selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
                binding.ivProductImage.setImageBitmap(selectedImageBitmap)
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), "Fail to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val updateProductViewModel: UpdateProductViewModel by lazy {
        ViewModelProvider(
            this,
            UpdateProductViewModel.UpdateProductViewModelFactory(requireActivity().application)
        )[UpdateProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productId = it.getInt("productId")
            if (productId != -1) {
                getProductById(productId)
            } else {
                Toast.makeText(requireContext(), "Invalid brand ID", Toast.LENGTH_SHORT).show()
            }
        }
        setupSpinner()
        binding.ivBack.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }

        binding.btnAddProductImage.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnUpdateProduct.setOnClickListener {
            updateProduct()
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun updateProduct() {
        val productName = binding.edtProductName.text.toString()
        val productDescription = binding.edtProductDescription.text.toString()
        val productPrice = binding.edtProductPrice.text.toString().toDouble()
        val productRecommendation = binding.cbRecommendation.isChecked

        if (productName.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter product name", Toast.LENGTH_SHORT).show()
            return
        }

        if (productDescription.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter product description", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (productPrice <= 0) {
            Toast.makeText(
                requireContext(),
                "Please enter available product price",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        if (selectedImageBitmap == null) {
            Toast.makeText(requireContext(), "Please select brand logo", Toast.LENGTH_SHORT).show()
            return
        }

        val product = Product(
            productId = productId,
            productName = productName,
            price = productPrice,
            description = productDescription,
            brandId = brandId,
            brandName = brandName,
            image = selectedImageBitmap!!,
            recommendation = productRecommendation,
        )

        updateProductViewModel.updateProduct(product)
        Toast.makeText(requireContext(), "Brand added successfully", Toast.LENGTH_SHORT).show()
        clearForm()

        replaceFragment(ProductManagerFragment())
    }

    private fun setupSpinner() {
        val spinnerBrand = binding.spinnerBrand

        updateProductViewModel.getAllBrands().observe(viewLifecycleOwner) { brands ->
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                brands.map { brand -> brand.brandName })
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBrand.adapter = arrayAdapter

            spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedBrand = brands[position]
                    Toast.makeText(
                        context,
                        "You have a selected: ${selectedBrand.brandId}",
                        Toast.LENGTH_SHORT
                    ).show()
                    brandId = selectedBrand.brandId
                    brandName = selectedBrand.brandName
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun getProductById(productId: Int) {
        updateProductViewModel.getProductById(productId).observe(viewLifecycleOwner) { product ->
            binding.edtProductName.setText(product.productName)
            binding.edtProductDescription.setText(product.description)
            binding.spinnerBrand.setSelection(product.brandId - 1)
            binding.edtProductPrice.setText(product.price.toString())
            product.image.let {
                binding.ivProductImage.setImageBitmap(it)
                selectedImageBitmap = it
            }
            binding.cbRecommendation.isChecked = product.recommendation
        }
    }

    private fun clearForm() {
        binding.edtProductName.text?.clear()
        binding.edtProductDescription.text?.clear()
        binding.edtProductPrice.text?.clear()
        binding.ivProductImage.setImageResource(R.drawable.ic_launcher_background)
        selectedImageBitmap = null
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }
}