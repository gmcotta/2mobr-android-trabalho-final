package com.gmcotta.a2mbor_trabalho_final.feature.register.presentation

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
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment: Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loginLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
                goToLogin()
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            if (!it.isNullOrBlank()) {
                val msg = when (it) {
                    "register_success_message" -> getString(R.string.register_success_message)
                    "required_fields_error_message" -> getString(R.string.required_fields_error_message)
                    "invalid_email_error_message" -> getString(R.string.invalid_email_error_message)
                    "registered_email_error_message" -> getString(R.string.registered_email_error_message)
                    "register_error_message" -> getString(R.string.register_error_message)
                    else -> getString(R.string.register_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupElements() {
        binding?.let {
            editTextEmail = it.tiEmail
            editTextPassword = it.tiPassword
            buttonRegister = it.btnRegister
            progressBar = it.progressBar
            loginLink = it.tvLoginLink
        }
    }

    private fun setupListeners() {
        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            progressBar.visibility = View.VISIBLE

            viewModel.signUp(email, password)
        }

        loginLink.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}