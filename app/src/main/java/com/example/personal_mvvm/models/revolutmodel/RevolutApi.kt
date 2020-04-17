//package com.example.personal_mvvm.models.revolutmodel
//
//import io.reactivex.Single
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.GET
//import retrofit2.http.POST
//
//interface RevolutApi{
//
//    @GET("base")
//    fun getBase():Single<List<BaseModel>>
//
//    @FormUrlEncoded
//    @POST("rate")
//    fun getRates(@Field("rates")rates:String): Single<List<BaseModel>>
//}