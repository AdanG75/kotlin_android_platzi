package com.platzi.conf2.viewModel

import com.platzi.conf2.model.Conference
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.conf2.network.Callback
import com.platzi.conf2.network.FirestoreService
import java.lang.Exception

//Se encarga de comunicar nuestro servicio de firebaser con nuestra UI
class ScheduleViewModel : ViewModel(){
    val firestoreService = FirestoreService()
    //Este tipo de lista permite la actualización en tiempo real dentro de nuestra UI
    var listSchedule: MutableLiveData<List<Conference>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getScheduleFromFirebase()
    }

    fun getScheduleFromFirebase() {
        firestoreService.getSchedule(object: Callback<List<Conference>>{
            override fun onSuccess(result: List<Conference>?) {
                listSchedule.postValue(result)
                println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX Mensaje XXXXXXXXXXXXXXXXXXXXXXXXXX")
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