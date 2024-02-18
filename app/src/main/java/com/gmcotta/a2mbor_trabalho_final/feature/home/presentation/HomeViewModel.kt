package com.gmcotta.a2mbor_trabalho_final.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthService
import com.gmcotta.a2mbor_trabalho_final.infra.firebase.FirebaseAuthServiceImpl
import com.gmcotta.a2mbor_trabalho_final.base.LoggedViewModel
import com.gmcotta.a2mbor_trabalho_final.feature.home.data.HomeRepositoryImpl
import com.gmcotta.a2mbor_trabalho_final.model.Event
import kotlinx.coroutines.launch

class HomeViewModel: LoggedViewModel() {
    private val homeRepository = HomeRepositoryImpl()
    private lateinit var firebaseAuth: FirebaseAuthService

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsList: LiveData<List<Event>>
        get() = _eventsList

    fun logout() {
        firebaseAuth = FirebaseAuthServiceImpl()
        firebaseAuth.signOut()
    }

    fun getEvents() {
        viewModelScope.launch {
            homeRepository.getEvents({
                val genericList = it as? List<*>
                _eventsList.postValue(genericList?.filterIsInstance<Event>())
            }, {
                _msg.value = "get_events_error_message"
            })
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            homeRepository.delete(event, {
                _msg.value = "delete_event_success_message"
            }, {
                _msg.value = "delete_event_error_message"
            })
        }
    }
}