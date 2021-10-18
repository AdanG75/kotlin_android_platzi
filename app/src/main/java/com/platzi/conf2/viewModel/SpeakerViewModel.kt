package com.platzi.conf2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.conf2.model.Speaker
import com.platzi.conf2.network.Callback
import com.platzi.conf2.network.FirestoreService
import java.lang.Exception

//Se encarga de comunicar nuestro servicio de firebaser con nuestra UI
class SpeakerViewModel : ViewModel(){
    val firestoreService = FirestoreService()
    //Este tipo de lista permite la actualización en tiempo real dentro de nuestra UI
    var listSpeaker: MutableLiveData<List<Speaker>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getSpeakerFromFirebase()
    }

    private fun getSpeakerFromFirebase() {
        firestoreService.getSpeakers(object: Callback<List<Speaker>> {
            override fun onSuccess(result: List<Speaker>?) {
                listSpeaker.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    private fun processFinished() {
        isLoading.value = true
    }
}