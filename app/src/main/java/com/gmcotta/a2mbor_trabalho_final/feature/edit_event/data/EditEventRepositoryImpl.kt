package com.gmcotta.a2mbor_trabalho_final.feature.edit_event.data

import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.gmcotta.a2mbor_trabalho_final.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditEventRepositoryImpl: EditEventRepository {
    override fun save(
        event: Event,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val eventToEdit = mapOf(
            "name" to event.name,
            "date" to event.date,
            "time" to event.time,
            "address" to event.address
        )
        Firebase.firestore.collection(Constants.FIRESTORE_COLLECTION_EVENTS).document(event.id)
            .update(eventToEdit)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}