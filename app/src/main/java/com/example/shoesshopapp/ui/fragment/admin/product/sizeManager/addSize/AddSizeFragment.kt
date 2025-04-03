package com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.addSize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentAddSizeBinding
import com.example.shoesshopapp.ui.fragment.admin.product.sizeManager.SizeManagerFragment

class AddSizeFragment : Fragment() {

    private lateinit var binding: FragmentAddSizeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            replaceFragment(SizeManagerFragment())
        }

        binding.btnAddSize.setOnClickListener {
            replaceFragment(SizeManagerFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clAdminHome, fragment)
        fragmentTransaction.commit()
    }

}