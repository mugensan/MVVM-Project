package com.example.personal_mvvm.models.util

import android.content.Context
import android.preference.PreferenceManager

//storing api key in the sharedPrefs
@Suppress("DEPRECATION")
class SharedPreferencesHelper (context:Context){

    private val PREF_API_KEY = "api key"

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    
    fun saveApiKey(key:String){
        //committing the api key to the sharedPref
        prefs.edit().putString(PREF_API_KEY,key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY,null)

}