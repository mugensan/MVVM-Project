package com.example.personal_mvvm.di.diapi

import com.example.personal_mvvm.models.animal.AnimalApiService
import dagger.Component
import javax.inject.Singleton


@Component(modules = [ApiModule::class])
interface ApiComponent {
    //this is where we want to inject into (AnimalApiService)
    fun inject(service: AnimalApiService)
}