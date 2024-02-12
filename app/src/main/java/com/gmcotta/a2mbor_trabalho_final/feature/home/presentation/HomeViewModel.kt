package com.gmcotta.a2mbor_trabalho_final.feature.home.presentation

import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuthService

    fun getUserEmail(): String? {
        firebaseAuth = FirebaseAuthServiceImpl()
        return firebaseAuth.getUser().email
    }

    fun logout() {
        firebaseAuth = FirebaseAuthServiceImpl()
        firebaseAuth.signOut()
    }
}