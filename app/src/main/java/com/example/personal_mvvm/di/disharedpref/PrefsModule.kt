package com.example.personal_mvvm.di.disharedpref

import android.app.Application
import com.example.personal_mvvm.models.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app:Application): SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }
}