package com.example.weatherforecastgeekkotlin.api

import com.example.weatherforecastgeekkotlin.models.DataModel
import com.google.gson.stream.JsonToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET("forecast")
    fun getWeather(
      @Header("X-Yandex-API-Key:") token: String,
      @Query("lat") lat: Double,
      @Query("lon") lon: Double,
      @Query("extra") extra: Boolean
    ): Call<DataModel>
}