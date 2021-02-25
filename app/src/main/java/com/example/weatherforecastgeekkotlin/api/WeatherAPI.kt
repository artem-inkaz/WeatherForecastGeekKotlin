package com.example.weatherforecastgeekkotlin.api

import com.example.weatherforecastgeekkotlin.models.DataModel
import com.google.gson.stream.JsonToken
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// запрос на сервер
interface WeatherAPI {
    @GET("v2/forecast")
    fun getWeather(
//      @Header("X-Yandex-API-Key:") token: String,
      @Query("lat") lat: Double,
      @Query("lon") lon: Double,
      @Query("extra") extra: Boolean
    ): Call<DataModel>
}