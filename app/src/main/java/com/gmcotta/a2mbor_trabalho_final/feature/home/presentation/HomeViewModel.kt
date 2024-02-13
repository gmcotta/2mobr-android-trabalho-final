package com.gmcotta.a2mbor_trabalho_final.feature.home.presentation

import android.util.Log
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmcotta.a2mbor_trabalho_final.feature.create_event.data.CreateEventRepositoryImpl
import com.gmcotta.a2mbor_trabalho_final.feature.home.data.HomeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val homeRepository = HomeRepositoryImpl()
    private lateinit var firebaseAuth: FirebaseAuthService


    fun getUserEmail(): String? {
        firebaseAuth = FirebaseAuthServiceImpl()
        return firebaseAuth.getUser().email
    }

    fun logout() {
        firebaseAuth = FirebaseAuthServiceImpl()
        firebaseAuth.signOut()
    }

    fun getEvents() {
        viewModelScope.launch {
            homeRepository.getEvents({
                Log.i("getEventsSuccess", it.toString())
            }, {
                Log.i("getEventsFailure", it.toString())
            })
        }

    }
}