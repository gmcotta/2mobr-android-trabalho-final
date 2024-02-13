package com.gmcotta.a2mbor_trabalho_final.feature.home.data

import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.gmcotta.a2mbor_trabalho_final.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl: HomeRepository {
    override suspend fun getEvents(onSuccess: (events: List<Event>) -> Unit, onFailure: (exception: Exception) -> Unit) {
        val eventsRef = Firebase.firestore.collection(Constants.FIRESTORE_COLLECTION_EVENTS)
        return try {
            val querySnapshot = eventsRef.get().await()
            val events = querySnapshot.toObjects(Event::class.java)
            onSuccess(events)
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }
}
