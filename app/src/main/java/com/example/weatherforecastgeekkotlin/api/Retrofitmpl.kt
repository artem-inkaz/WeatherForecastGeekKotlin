package com.example.weatherforecastgeekkotlin.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create



class Retrofitmpl {
    fun getWeatherApi(): WeatherAPI{
        val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .baseUrl("https://api.weather.yandex.ru/v2/")
            .build()
        return retrofit.create(WeatherAPI::class.java)
    }
}