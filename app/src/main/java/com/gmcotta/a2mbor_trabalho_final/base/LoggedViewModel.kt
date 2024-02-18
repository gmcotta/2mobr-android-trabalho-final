package com.gmcotta.a2mbor_trabalho_final.base

import androidx.lifecycle.ViewModel
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl

open class LoggedViewModel: ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuthService
    fun getUserEmail(): String? {
        firebaseAuth = FirebaseAuthServiceImpl()
        return firebaseAuth.getUser()?.email
    }
}