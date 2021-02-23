package com.example.weatherforecastgeekkotlin.api

import com.example.weatherforecastgeekkotlin.models.DataModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {
//    companion object {
//        fun getWeatherApi(): ApiService{
//            val retrofit: Retrofit = Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(
//                    GsonConverterFactory.create(
//                        GsonBuilder().setLenient().create()
//                    )
//                )
//                .baseUrl("https://api.weather.yandex.ru/")
//                .build()
//            return retrofit.create(ApiService::class.java)
//        }
//
//    }
//
//    @GET("v2/forecast")
//    fun getWeather(
//        @Header("X-Yandex-API-Key:") token: String,
//        @Query("lat") lat: Double,
//        @Query("lon") lon: Double,
//        @Query("extra") extra: Boolean
//    ): Call<DataModel>

}
