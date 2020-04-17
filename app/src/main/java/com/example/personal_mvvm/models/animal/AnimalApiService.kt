package com.example.personal_mvvm.models.animal

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL_ANIMAL = "https://us-central1-apis-4674e.cloudfunctions.net/"

class AnimalApiService {
    private val apiAnimal = Retrofit.Builder()
        .baseUrl(BASE_URL_ANIMAL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey(): Single<ApiKey> {
        return apiAnimal.getAPIKey()
    }

    fun getAnimal(key:String):Single<List<Animal>>{
        return apiAnimal.getAnimals(key)
    }

}