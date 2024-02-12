package com.gmcotta.a2mbor_trabalho_final.feature.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.databinding.ActivityRegisterBinding
import com.gmcotta.a2mbor_trabalho_final.feature.login.LoginActivity
import com.gmcotta.a2mbor_trabalho_final.feature.register.presentation.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: RegisterViewModel

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = RegisterViewModel()

        setupObservers()
        setupElements()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.status.observe(this) {
            progressBar.visibility = View.GONE
            if (it) {
                goToLogin()
            }
        }

        viewModel.msg.observe(this) {
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
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupElements() {
        editTextEmail = binding.tiEmail
        editTextPassword = binding.tiPassword
        buttonRegister = binding.btnRegister
        progressBar = binding.progressBar
        loginLink = binding.tvLoginLink
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
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}