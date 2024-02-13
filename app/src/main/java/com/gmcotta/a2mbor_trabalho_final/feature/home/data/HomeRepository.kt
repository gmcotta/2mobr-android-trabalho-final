package com.gmcotta.a2mbor_trabalho_final.feature.home.data

import com.gmcotta.a2mbor_trabalho_final.model.Event

interface HomeRepository {
    suspend fun getEvents(onSuccess: (events: List<Event>) -> Unit, onFailure: (exception: Exception) -> Unit)
}