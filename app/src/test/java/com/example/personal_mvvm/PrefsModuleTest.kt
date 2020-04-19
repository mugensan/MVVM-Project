package com.example.personal_mvvm

import android.app.Application
import com.example.personal_mvvm.di.disharedpref.PrefsModule
import com.example.personal_mvvm.models.util.SharedPreferencesHelper

//i don't want to use the real sharedPref but mock instead
class PrefsModuleTest(val mockPrefs:SharedPreferencesHelper):PrefsModule() {

    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}