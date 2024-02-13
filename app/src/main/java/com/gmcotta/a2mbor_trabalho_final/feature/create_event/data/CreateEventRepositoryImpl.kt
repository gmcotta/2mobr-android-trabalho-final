package com.gmcotta.a2mbor_trabalho_final.feature.create_event.data

import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.gmcotta.a2mbor_trabalho_final.utils.Constants
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateEventRepositoryImpl: CreateEventRepository {
    override fun save(event: Event, onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        Firebase.firestore.collection(Constants.FIRESTORE_COLLECTION_EVENTS).document(event.id)
            .set(event, SetOptions.merge())
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}