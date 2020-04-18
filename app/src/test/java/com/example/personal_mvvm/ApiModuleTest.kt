package com.example.personal_mvvm

import com.example.personal_mvvm.di.diapi.ApiModule
import com.example.personal_mvvm.models.animal.AnimalApiService

class ApiModuleTest(val mockService:AnimalApiService):ApiModule() {
    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}