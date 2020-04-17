package com.example.personal_mvvm.models.animal

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

//83d4f576e99be0a4625684f3b4e5829754e6ec76
//getting info from backend Api
interface AnimalApi {

    //single is an observable
    @GET("getKey")
    fun getAPIKey(): Single<ApiKey>

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String): Single<List<Animal>>
}
