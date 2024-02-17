package com.gmcotta.a2mbor_trabalho_final.feature.edit_event.data

import com.gmcotta.a2mbor_trabalho_final.model.Event

interface EditEventRepository {
    fun save(event: Event, onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
}