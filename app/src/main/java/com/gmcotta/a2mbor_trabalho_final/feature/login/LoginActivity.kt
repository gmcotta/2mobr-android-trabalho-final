package com.gmcotta.a2mbor_trabalho_final.feature.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.feature.register.RegisterActivity
import com.gmcotta.a2mbor_trabalho_final.databinding.ActivityLoginBinding
import com.gmcotta.a2mbor_trabalho_final.feature.home.HomeActivity
import com.gmcotta.a2mbor_trabalho_final.feature.login.presentation.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: LoginViewModel

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var registerLink: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = LoginViewModel()

        setupObservers()
        setupElements()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.status.observe(this) {
            progressBar.visibility = View.GONE
            if (it) {
                goToHome()
            }

        }

        viewModel.msg.observe(this) {
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
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupElements() {
        editTextEmail = binding.tiEmail
        editTextPassword = binding.tiPassword
        buttonLogin = binding.btnLogin
        progressBar = binding.progressBar
        registerLink = binding.tvRegisterLink
    }

    private fun setupListeners() {
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            progressBar.visibility = View.VISIBLE

            viewModel.signIn(email, password)

        }

        registerLink.setOnClickListener {
            goToRegister()
        }
    }

    private fun goToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToHome() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}