package com.example.shoesshopapp.ui.fragment.admin.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentSettingBinding
import com.example.shoesshopapp.model.utils.SessionManager

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val sessionManager: SessionManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clLogout.setOnClickListener {
            sessionManager?.logout()
            findNavController().navigate(R.id.action_adminHomeFragment_to_loginFragment)
        }

    }
}