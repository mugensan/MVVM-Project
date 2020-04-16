package com.example.personal_mvvm.models.animal

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST


//getting info from backend Api
interface AnimalApi {

    //single is an observable
    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    @POST("getAnimals")
    fun getAnimals(@Field("key")key:String):Single<List<Animal>>
}