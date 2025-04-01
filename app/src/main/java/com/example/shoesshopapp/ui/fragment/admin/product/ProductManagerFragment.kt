package com.example.shoesshopapp.ui.fragment.admin.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentProductManagerBinding
import com.example.shoesshopapp.ui.fragment.admin.product.addProduct.AddProductFragment
import com.example.shoesshopapp.ui.fragment.admin.product.addSize.SizeManagerFragment


class ProductManagerFragment : Fragment() {

    private lateinit var binding: FragmentProductManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddProduct.setOnClickListener {
            replaceFragment(AddProductFragment())
        }

        binding.btnItem.setOnClickListener {
            replaceFragment(SizeManagerFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }
}