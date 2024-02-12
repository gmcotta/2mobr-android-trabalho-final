package com.gmcotta.a2mbor_trabalho_final.feature.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterViewModel: ViewModel() {
    private lateinit var firebaseAuthService: FirebaseAuthService

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun signUp(email: String, password: String) {

        firebaseAuthService = FirebaseAuthServiceImpl()

        if(email.isEmpty() || password.isEmpty()) {
            _msg.value = "required_fields_error_message"
            return
        }

        val task = firebaseAuthService.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            _msg.value = "register_success_message"
            _status.value = true
        }
            .addOnFailureListener{
                _msg.value = try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    "invalid_email_error_message"
                } catch (e: FirebaseAuthUserCollisionException) {
                    "registered_email_error_message"
                } catch (e: Exception) {
                    "register_error_message"
                }
            }
    }
}