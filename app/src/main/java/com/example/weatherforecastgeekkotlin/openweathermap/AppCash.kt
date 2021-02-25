package com.example.weatherforecastgeekkotlin.openweathermap

import android.app.Activity
import android.content.SharedPreferences

internal class AppCash(activity: Activity) {
    private val userPreferences: SharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE)
    val savedCity: String?
        get() = userPreferences.getString(CITY_KEY, DEFAULT_TOWN)

    fun saveCity(city: String?) {
        userPreferences.edit().putString(CITY_KEY, city).apply()
    }



    companion object {
        private const val CITY_KEY = "city"
        private const val DEFAULT_TOWN = "Moscow"
    }

}