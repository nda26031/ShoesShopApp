package com.example.shoesshopapp.ui.fragment.users.cart

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentCartBinding
import com.example.shoesshopapp.model.data.Product
import com.example.shoesshopapp.model.dataTest.CartProduct
import com.example.shoesshopapp.ui.fragment.users.cart.thankyou.CartProductAdapter
import com.example.shoesshopapp.ui.fragment.users.cart.thankyou.ThankYouFragment
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardFragment


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartProducts = listOf(
        CartProduct(1, "Air force 1", 400.0, 1, R.drawable.poster6),
        CartProduct(2, "Pumaaa", 400.0, 1, R.drawable.poster6),
        CartProduct(3, "Adadis", 400.0, 1, R.drawable.poster6),
    )

    private val cartProductAdapter = CartProductAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            replaceFragment(DashboardFragment())
        }

        binding.btnCheckOut.setOnClickListener {
            showCheckoutConfirmation()
        }

        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        binding.rvCart.adapter = cartProductAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(context)
        cartProductAdapter.submitList(cartProducts)
    }

    private fun showCheckoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Check out cart")
            .setMessage("Are you sure you want to check out ?")
            .setPositiveButton("Yes") { _, _ ->
                replaceFragment(ThankYouFragment())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}