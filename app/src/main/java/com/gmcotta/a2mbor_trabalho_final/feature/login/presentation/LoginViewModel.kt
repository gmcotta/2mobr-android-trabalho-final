package com.gmcotta.a2mbor_trabalho_final.feature.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel : ViewModel() {

    private lateinit var firebaseAuthService: FirebaseAuthService

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun signIn(email: String, password: String) {

        firebaseAuthService = FirebaseAuthServiceImpl()

        if(email.isEmpty() || password.isEmpty()) {
            _msg.value = "required_fields_error_message"
            return
        }

        val task = firebaseAuthService.signIn(email, password);

        task
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "login_success_message"
            }
            .addOnFailureListener {
                _msg.value = try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    "login_invalid_user_message"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    "login_invalid_credentials_message"
                } catch (e: Exception) {
                    "login_error_message"
                }
            }
    }
}