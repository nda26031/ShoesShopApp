package com.example.shoesshopapp.ui.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAdminHomeBinding
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.order.OrderManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.product.ProductManagerFragment
import com.example.shoesshopapp.ui.fragment.admin.setting.SettingFragment


class AdminHomeFragment : Fragment() {

    private lateinit var binding: FragmentAdminHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceFragment(BrandManagerFragment())
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.brand -> {
                    replaceFragment(BrandManagerFragment())
                    true
                }

                R.id.product -> {
                    replaceFragment(ProductManagerFragment())
                    true
                }

                R.id.order -> {
                    replaceFragment(OrderManagerFragment())
                    true
                }

                R.id.setting -> {
                    replaceFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}