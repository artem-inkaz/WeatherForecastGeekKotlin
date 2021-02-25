package com.example.weatherforecastgeekkotlin.openweathermap.api

import com.example.weatherforecastgeekkotlin.openweathermap.models.DataModelMap
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

// запрос на сервер
interface OpenWeatherAPI {
    @GET("data/2.5/weather")
    fun getOpenWeather(
        @Query ("q") q: String,
        @Query ("apikey") token: String,
        @Query ("units") units: String,
//        @Query ("metric") metric: String
    ): Call<DataModelMap>
}