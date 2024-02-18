package com.gmcotta.a2mbor_trabalho_final.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.gmcotta.a2mbor_trabalho_final.R

open class LoggedFragment: Fragment() {
    private val loggedViewModel: LoggedViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIfUserIsLogged()
    }

    private fun checkIfUserIsLogged() {
        val email = loggedViewModel.getUserEmail()
        Log.i("checkIfUserIsLogged", email.toString())
        if (email == null) {
            Toast.makeText(requireContext(), getString(R.string.session_user_not_loggedin_error_message), Toast.LENGTH_LONG).show()
            navigateToLogin()
            return
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.back_to_login)
//        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}