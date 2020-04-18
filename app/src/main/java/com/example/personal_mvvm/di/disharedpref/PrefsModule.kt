package com.example.personal_mvvm.di.disharedpref

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.personal_mvvm.models.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPreferences(app:Application): SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }

    //if i need to use the same sharedPref with another activity
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: AppCompatActivity) : SharedPreferencesHelper{
        return SharedPreferencesHelper(activity)
    }
}

//this differenciate which sharedPref is used by whom

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVITY = "Activity context"

@Qualifier
annotation class TypeOfContext(val type:String)