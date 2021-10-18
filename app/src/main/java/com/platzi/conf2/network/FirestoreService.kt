package com.platzi.conf2.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.platzi.conf2.model.Conference
import com.platzi.conf2.model.Speaker

const val CONFERENCES_COLLECTION_NAME = "conferences"
const val SPEAKERS_COLLECTION_NAME = "speakers"

class FirestoreService {
    //obtenemos instancia de Firebase
    val firebaseFirestore = FirebaseFirestore.getInstance()
    //activamos la persitencia offline de fiebase
    val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    //inicializador que agrega nuestra configuracion a nuestra instancia de firebase
    init {
        firebaseFirestore.firestoreSettings = settings
    }

    fun getSpeakers(callback: Callback<List<Speaker>>) {
        firebaseFirestore.collection(SPEAKERS_COLLECTION_NAME)
            .orderBy("category")
            .get()
            .addOnSuccessListener { result ->
                /*for (doc in result) {
                    val list = result.toObjects(Speaker::class.java)
                    callback.onSuccess(list)
                    break
                }*/
                callback.onSuccess(result.toObjects(Speaker::class.java))
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    fun getSchedule(callback: Callback<List<Conference>>) {
        firebaseFirestore.collection(CONFERENCES_COLLECTION_NAME)
            .orderBy("speaker")
            .get()
            .addOnSuccessListener { result ->
                callback.onSuccess(result.toObjects(Conference::class.java))
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }
}