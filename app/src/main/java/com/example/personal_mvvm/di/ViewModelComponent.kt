package com.example.personal_mvvm.di

import com.example.personal_mvvm.di.diapi.ApiModule
import com.example.personal_mvvm.di.diapp.AppModule
import com.example.personal_mvvm.di.disharedpref.PrefsModule
import com.example.personal_mvvm.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class,AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}