package com.gmcotta.a2mbor_trabalho_final.feature.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginFragment: Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var registerLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupElements()
        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupObservers() {
        viewModel.status.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            if (it) {
                navigateToHome()
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            if (!it.isNullOrBlank()) {
                val msg = when(it) {
                    "login_success_message" -> getString(R.string.login_success_message)
                    "required_fields_error_message" -> getString(R.string.required_fields_error_message)
                    "login_invalid_user_message" -> getString(R.string.login_invalid_user_message)
                    "login_invalid_credentials_message" -> getString(R.string.login_invalid_credentials_message)
                    "login_error_message" -> getString(R.string.login_error_message)
                    else -> getString(R.string.login_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupElements() {
        binding?.let {
            editTextEmail = it.tiEmail
            editTextPassword = it.tiPassword
            buttonLogin = it.btnLogin
            progressBar = it.progressBar
            registerLink = it.tvRegisterLink
        }
    }

    private fun setupListeners() {
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            progressBar.visibility = View.VISIBLE
            viewModel.signIn(email, password)
        }

        registerLink.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}