package com.example.shoesshopapp.ui.fragment.introduce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentIntroduceBinding

class IntroduceFragment : Fragment() {

    private lateinit var binding : FragmentIntroduceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroduceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: IntroduceFragment")

        binding.btnNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_introduceFragment_to_loginFragment)
        }
    }


}