package com.example.shoesshopapp.ui.fragment.users

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAdminHomeBinding
import com.example.shoesshopapp.databinding.FragmentUserHomeBinding
import com.example.shoesshopapp.model.utils.SessionManager
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
    private var accountId: Int = -1
    private var userId: Int = -1

    private val userHomeViewModel: UserHomeViewModel by lazy {
        ViewModelProvider(
            this, UserHomeViewModel.UserHomeViewModelFactory(requireActivity().application)
        )[UserHomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        arguments?.let {
            accountId = it.getInt("accountId")
        }
        getUserByAccount(accountId)
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.dashboard -> {
                    binding.tvUserHomeTitle.visibility = View.GONE
                    replaceFavouriteWithUserId(DashboardFragment())
                    true
                }

                R.id.brand -> {
                    binding.tvUserHomeTitle.visibility = View.GONE
                    replaceFragment(BrandFragment())
                    true
                }

                R.id.product -> {
                    binding.tvUserHomeTitle.visibility = View.GONE
                    replaceFavouriteWithUserId(ProductFragment())
                    true
                }

                R.id.order -> {
                    binding.tvUserHomeTitle.visibility = View.GONE
                    replaceFavouriteWithUserId(OrderFragment())
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

    private fun getUserByAccount(accountId: Int) {
        userHomeViewModel.getUserByAccount(accountId).observe(viewLifecycleOwner) { user ->
            userId = user.userId
            Log.d("userId in getUserByAccount ", userId.toString())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }

    private fun replaceFavouriteWithUserId(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putInt("userId", userId)
        Log.d("userId in userHome ", userId.toString())
        fragment.arguments = bundle
        replaceFragment(fragment)
    }
}