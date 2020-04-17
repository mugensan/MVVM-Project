//package com.example.personal_mvvm.models.revolutmodel
//
//import io.reactivex.Single
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//
//private val BASE_URL_REVOLUT = "https://hiring.revolut.codes/api/android/"//latest"
//
//class RevolutApiService {
//
//    private val apiRevolutService = Retrofit.Builder()
//        .baseUrl(BASE_URL_REVOLUT)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//        .create(RevolutApiService::class.java)
//
//    fun getBase(): Single<BaseModel> {
//        return apiRevolutService.getBase()
//    }
//
//    fun getRates(rates:String):Single<List<BaseModel>>{
//        return apiRevolutService.getRates(rates)
//    }
//}