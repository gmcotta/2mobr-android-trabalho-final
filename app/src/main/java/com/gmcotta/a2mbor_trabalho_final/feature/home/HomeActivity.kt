package com.gmcotta.a2mbor_trabalho_final.feature.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.databinding.ActivityHomeBinding
import com.gmcotta.a2mbor_trabalho_final.feature.home.presentation.HomeViewModel
import com.gmcotta.a2mbor_trabalho_final.feature.login.LoginActivity

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: HomeViewModel

    private lateinit var buttonLogout: Button
    private lateinit var emailText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = HomeViewModel()

        setupElements()
        setupTexts()
        setupListeners()
    }

    private fun setupElements() {
        buttonLogout = binding.btnLogout
        emailText = binding.tvUserEmail
    }

    private fun setupTexts() {
        val email = viewModel.getUserEmail()
        if (email == null) {
            Toast.makeText(this, getString(R.string.session_user_not_loggedin_error_message), Toast.LENGTH_LONG).show()
            goToLogin()
        }
        emailText.text = email
    }

    private fun setupListeners() {
        buttonLogout.setOnClickListener {

            viewModel.logout()
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}