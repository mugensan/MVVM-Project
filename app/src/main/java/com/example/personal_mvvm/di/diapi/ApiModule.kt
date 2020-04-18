package com.example.personal_mvvm.di.diapi

import com.example.personal_mvvm.models.animal.AnimalApi
import com.example.personal_mvvm.models.animal.AnimalApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL_ANIMAL = "https://us-central1-apis-4674e.cloudfunctions.net/"


//this the class i want to inject
@Module
class ApiModule {

    @Provides
    fun provideAnimalApi():AnimalApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_ANIMAL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalApi::class.java)
    }

    @Provides
    fun provideAnimalApiService(): AnimalApiService{
        return AnimalApiService()
    }
}