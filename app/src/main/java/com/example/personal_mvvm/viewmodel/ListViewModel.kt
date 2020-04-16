package com.example.personal_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.personal_mvvm.models.animal.Animal

//exposing a series of liveData from the backend and our api will need a key
class ListViewModel(application: Application):AndroidViewModel(application) {


    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    //start the retrieval of the information of the backend
    //Test Mock Data
    fun refresh(){
        getAnimals()
    }

    //dummy data for test
    private fun getAnimals(){
        val a1 = Animal("alligator")
        val a2 = Animal("bee")
        val a3 = Animal("cat")
        val a4 = Animal("dog")
        val a5 = Animal("elephant")
        val a6 = Animal("flamengo")

        val animalList = arrayListOf(a1,a2,a3,a4,a5,a6)
        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}