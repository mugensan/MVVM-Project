package com.example.personal_mvvm.viewmodel

//import com.example.personal_mvvm.models.revolutmodel.BaseModel
//import com.example.personal_mvvm.models.revolutmodel.RevolutApiService
//import com.example.personal_mvvm.models.revolutmodel.BaseModel
//import com.example.personal_mvvm.models.revolutmodel.Rates
//import com.example.personal_mvvm.models.revolutmodel.RevolutApiService
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.personal_mvvm.di.DaggerViewModelComponent
import com.example.personal_mvvm.di.diapp.AppModule
import com.example.personal_mvvm.di.disharedpref.CONTEXT_APP
import com.example.personal_mvvm.di.disharedpref.TypeOfContext
import com.example.personal_mvvm.models.animal.Animal
import com.example.personal_mvvm.models.animal.AnimalApiService
import com.example.personal_mvvm.models.animal.ApiKey
import com.example.personal_mvvm.models.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Field
import javax.inject.Inject

//exposing a series of liveData from the backend and our api will need a key
class ListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application,test:Boolean = true):this(application){
        injected = true
    }

    //d718b0a923bd5bca385968a6f81e2e18fddb992a

    //    val rates by lazy { MutableLiveData<List<BaseModel>>() }
//    val baseCurrency by lazy { MutableLiveData<List<BaseModel>>() }
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    private var injected = false

    @Inject
    lateinit var apiAnimalService: AnimalApiService

    //PREFS
    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs :SharedPreferencesHelper

    //avoid infinite loop for the key error (flag)
    private var invalidApiKey = false

        fun inject() {
            if(!injected)
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }

    //start the retrieval of the information of the backend
    //Test Mock Data
    fun refresh() {
        inject()
        loading.value = true
        //avoiding the infinite loop (key->error), start with flag = false
        invalidApiKey = false
        //checking I have a key and if yes return the list
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    //creating another refresh every time the screen get refreshed no need to get it from
    //sharedPrefs, just getKey() function call in listFragment
    fun hardRefresh() {
        inject()
        loading.value = true
        getKey()
    }

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
                            prefs.saveApiKey(key.key)
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
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }

                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}





