package com.gmcotta.a2mbor_trabalho_final.feature.create_event.data

import com.gmcotta.a2mbor_trabalho_final.model.Event

interface CreateEventRepository {
    fun save(event: Event, onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
}