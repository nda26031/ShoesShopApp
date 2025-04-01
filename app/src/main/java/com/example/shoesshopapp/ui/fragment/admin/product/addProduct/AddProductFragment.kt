package com.example.shoesshopapp.ui.fragment.admin.product.addProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAddProductBinding
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }

        binding.btnAddProduct.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }
}