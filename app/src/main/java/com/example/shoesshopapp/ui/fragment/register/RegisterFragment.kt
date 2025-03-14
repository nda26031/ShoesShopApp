package com.example.shoesshopapp.ui.fragment.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(
            this,
            RegisterViewModel.RegisterViewModelFactory(requireActivity().application)
        )[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        cPasswordFocusListener()
        nameFocusListener()
        addressFocusListener()
        phoneFocusListener()


        // Thực hiện đăng ký tài khoản và lưu thông tin người dùng vào cơ sở dữ liệu

        binding.apply {
            tvLogin.setOnClickListener {
                navigate(R.id.action_registerFragment_to_loginFragment)
            }
            btnRegister.setOnClickListener {
                val username = binding.edtUsername.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val fullName = binding.edtFullName.text.toString()
                val address = binding.edtAddress.text.toString()
                val phone = binding.edtPhone.text.toString()
                registerViewModel.registerAccount(
                    username,
                    email,
                    password,
                    fullName,
                    address,
                    phone
                )
                registerViewModel.registerResult.observe(viewLifecycleOwner) { success ->
                    Log.d("Success","Sucess $success")
                    if (success) {
                        Toast.makeText(
                            requireContext(),
                            "Registration Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Email already exists", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun usernameFocusListener() {
        binding.edtUsername.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilUsername.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String? {
        val usernameText = binding.edtUsername.text.toString()
        if (usernameText.length > 20) {
            return "Maximum 20 Character Password"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.edtEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {

        val emailText = binding.edtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.edtPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {

        val passwordText = binding.edtPassword.text.toString()

        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        } else if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

    private fun cPasswordFocusListener() {
        binding.edtConfirmPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilConfirmPassword.helperText = validCPassword()
            }
        }
    }

    private fun validCPassword(): String? {

        val cPasswordText = binding.edtConfirmPassword.text.toString()
        val passwordText = binding.edtPassword.text.toString()

        if (cPasswordText != passwordText) {
            return "Password Not Match"
        }
        return null
    }

    private fun nameFocusListener() {
        binding.edtFullName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilFullName.helperText = validName()
            }
        }
    }

    private fun validName(): String? {

        val usernameText = binding.edtFullName.text.toString()
        if (usernameText.length > 20) {
            return "Maximum 20 Character Password"
        }
        return null
    }

    private fun addressFocusListener() {
        binding.edtAddress.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilAddress.helperText = validAddress()
            }
        }
    }

    private fun validAddress(): String? {

        val usernameText = binding.edtAddress.text.toString()
        if (usernameText.length > 100) {
            return "Maximum 100 Character Password"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.edtPhone.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilPhone.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {

        val usernameText = binding.edtPhone.text.toString()
        if (usernameText.length > 10) {
            return "Maximum 10 Character Password"
        }
        return null
    }


    private fun navigate(action: Int) {
        findNavController().navigate(action)
    }


}