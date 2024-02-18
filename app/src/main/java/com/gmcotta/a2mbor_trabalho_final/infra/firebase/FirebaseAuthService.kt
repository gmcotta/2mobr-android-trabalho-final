package com.gmcotta.a2mbor_trabalho_final.infra.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthService {
    fun signIn(email: String, password: String): Task<AuthResult>
    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun getUser(): FirebaseUser?
    fun signOut()
}