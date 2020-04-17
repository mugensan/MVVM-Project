package com.example.personal_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.animal.AnimalApiService
import com.example.personal_mvvm.models.animal.ApiKey
//import com.example.personal_mvvm.models.revolutmodel.BaseModel
//import com.example.personal_mvvm.models.revolutmodel.RevolutApiService
//import com.example.personal_mvvm.models.revolutmodel.BaseModel
//import com.example.personal_mvvm.models.revolutmodel.Rates
//import com.example.personal_mvvm.models.revolutmodel.RevolutApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

//exposing a series of liveData from the backend and our api will need a key
class ListViewModel(application: Application) : AndroidViewModel(application) {

    //d718b0a923bd5bca385968a6f81e2e18fddb992a

//    val rates by lazy { MutableLiveData<List<BaseModel>>() }
//    val baseCurrency by lazy { MutableLiveData<List<BaseModel>>() }
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiAnimalService = AnimalApiService()
//    private val apiRevolutService = RevolutApiService()

    //start the retrieval of the information of the backend
    //Test Mock Data
    fun refresh() {
        loading.value = true
        getKey()

//        getBase()
//        getRates("EUR")
    }

//
//
//    private fun getBase() {
//        disposable.add(
//            apiRevolutService.getBase()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<BaseModel>() {
//                    override fun onSuccess(baseCurrency: BaseModel) {
//                        if (baseCurrency.baseCurrency.isNullOrEmpty()) {
//                            loadError.value = true
//                            loading.value = false
//                        } else {
//                            getRates(baseCurrency.baseCurrency)
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        e.printStackTrace()
//                        loading.value = false
//                        loadError.value = true
//                    }
//
//                }
//                )
//        )
//    }
//
//    private fun getRates(base: String) {
//        disposable.add(
//            apiRevolutService.getRates("EUR")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<List<BaseModel>>() {
//                    override fun onSuccess(t: List<BaseModel>) {
//                        loadError.value = false
//                        baseCurrency.value = t
//                        loading.value = false
//                    }
//
//                    override fun onError(e: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//
//
//                }
//                )
//        )
//
//    }
//


    private fun getKey() {
        disposable.add(
            apiAnimalService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }

                }
                )
        )

    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiAnimalService.getAnimal(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        animals.value = null
                        loadError.value = true
                    }

                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}





