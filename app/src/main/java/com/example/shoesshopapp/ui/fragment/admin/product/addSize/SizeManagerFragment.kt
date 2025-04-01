package com.example.shoesshopapp.ui.fragment.admin.product.addSize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentSizeManagerBinding
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment

class SizeManagerFragment : Fragment() {

    private lateinit var binding : FragmentSizeManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSizeManagerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddSize.setOnClickListener {
            replaceFragment(AddSizeFragment())
        }

        binding.ivBack.setOnClickListener {
            replaceFragment(ProductManagerFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}