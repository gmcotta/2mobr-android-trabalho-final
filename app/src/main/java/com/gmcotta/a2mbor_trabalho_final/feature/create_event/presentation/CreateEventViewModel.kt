package com.gmcotta.a2mbor_trabalho_final.feature.create_event.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmcotta.a2mbor_trabalho_final.base.LoggedViewModel
import com.gmcotta.a2mbor_trabalho_final.feature.create_event.data.CreateEventRepositoryImpl
import com.gmcotta.a2mbor_trabalho_final.model.Event

class CreateEventViewModel : LoggedViewModel() {
    private val createEventRepository = CreateEventRepositoryImpl()

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun saveEvent(event: Event) {
        if (event.name.isNullOrBlank() || event.address.isNullOrBlank() || event.time == null || event.date == null) {
            _msg.value = "required_fields_error_message"
            return
        }
        createEventRepository.save(event, {
            _msg.value = "save_event_success_message"
        }) {
            _msg.value = "save_event_error_message"
        }
    }
}