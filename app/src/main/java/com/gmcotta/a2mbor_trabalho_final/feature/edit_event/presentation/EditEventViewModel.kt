package com.gmcotta.a2mbor_trabalho_final.feature.edit_event.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmcotta.a2mbor_trabalho_final.base.LoggedViewModel
import com.gmcotta.a2mbor_trabalho_final.feature.edit_event.data.EditEventRepositoryImpl
import com.gmcotta.a2mbor_trabalho_final.model.Event

class EditEventViewModel: LoggedViewModel() {
    private val editEventRepository = EditEventRepositoryImpl()
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun saveEvent(event: Event?) {
        if (event == null || event.name.isNullOrBlank() || event.address.isNullOrBlank() || event.time == null || event.date == null) {
            _msg.value = "required_fields_error_message"
            return
        }
        editEventRepository.save(event, {
            _msg.value = "edit_event_success_message"
        }) {
            _msg.value = "edit_event_error_message"
        }
    }
}