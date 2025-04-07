package com.example.shoesshopapp.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentLoginBinding
import com.example.shoesshopapp.model.utils.SessionManager


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModel.LoginViewModelFactory(this.requireActivity().application)
        )[LoginViewModel::class.java]
    }
    private val sessionManager: SessionManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            viewModel.login(email, password)

            viewModel.account.observe(viewLifecycleOwner) { account ->
                if (account != null && account.email == email && account.password == password) {
                    sessionManager?.saveUser(account.username, account.role)
                    if (account.role == "admin") {

                        navigate(R.id.action_loginFragment_to_adminHomeFragment)
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("accountId", account.id)
                        findNavController().navigate(R.id.action_loginFragment_to_userHomeFragment, bundle)

                    }
                } else {
                    Toast.makeText(context, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun navigate(action: Int) {
        findNavController().navigate(action)
    }
}


