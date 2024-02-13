package com.gmcotta.a2mbor_trabalho_final.feature.create_event.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmcotta.a2mbor_trabalho_final.model.Event

class CreateEventViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun saveEvent(event: Event) {
        if (event.name.isNullOrBlank() || event.address.isNullOrBlank() || event.time == null || event.date == null) {
            Log.i("saveEvent", event.toString())
            _msg.value = "required_fields_error_message"
            return
        }
        // TODO: salva dados no Firestore
        _status.value = true
        _msg.value = "save_event_success_message"
    }
}