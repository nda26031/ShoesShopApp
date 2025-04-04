package com.example.shoesshopapp.ui.fragment.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAdminHomeBinding
import com.example.shoesshopapp.databinding.FragmentUserHomeBinding
import com.example.shoesshopapp.ui.fragment.admin.brand.BrandManagerFragment
import com.example.shoesshopapp.ui.fragment.users.account.AccountFragment
import com.example.shoesshopapp.ui.fragment.users.brand.BrandFragment
import com.example.shoesshopapp.ui.fragment.users.cart.CartFragment
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardFragment
import com.example.shoesshopapp.ui.fragment.users.favourite.FavouriteProductFragment
import com.example.shoesshopapp.ui.fragment.users.order.OrderFragment
import com.example.shoesshopapp.ui.fragment.users.product.ProductFragment


class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentUserHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceFragment(DashboardFragment())


        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    replaceFragment(DashboardFragment())
                    true
                }

                R.id.brand -> {
                    replaceFragment(BrandFragment())
                    true
                }

                R.id.product -> {
                    replaceFragment(ProductFragment())
                    true
                }

                R.id.order -> {
                    replaceFragment(OrderFragment())
                    true
                }

                R.id.account -> {
                    replaceFragment(AccountFragment())
                    true
                }

                else -> false
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }

}