package com.example.personal_mvvm.models.revolutmodel


import com.google.gson.annotations.SerializedName

data class BaseModel(
    val baseCurrency: String,
    val rates: Rates
)