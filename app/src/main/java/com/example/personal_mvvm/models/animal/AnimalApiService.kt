package com.example.personal_mvvm.models.animal

import com.example.personal_mvvm.di.diapi.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject


class AnimalApiService {

    //receiving the injection
    @Inject
    lateinit var apiAnimal: AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return apiAnimal.getAPIKey()
    }

    fun getAnimal(key: String): Single<List<Animal>> {
        return apiAnimal.getAnimals(key)
    }

}