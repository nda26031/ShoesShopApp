package com.example.shoesshopapp.ui.fragment.users.cart.thankyou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentThankYouBinding
import com.example.shoesshopapp.ui.fragment.users.order.OrderFragment

class ThankYouFragment : Fragment() {

    private lateinit var binding: FragmentThankYouBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentThankYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTrackOrder.setOnClickListener {
            replaceFragment(OrderFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}