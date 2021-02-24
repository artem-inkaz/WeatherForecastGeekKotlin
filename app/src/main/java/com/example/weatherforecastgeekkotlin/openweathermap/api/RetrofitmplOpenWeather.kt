package com.example.weatherforecastgeekkotlin.openweathermap.api

import com.example.weatherforecastgeekkotlin.api.WeatherAPI
import com.example.weatherforecastgeekkotlin.api2.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
private const val OPEN_WEATHER_MAP_API1 =
    "http://api.openweathermap.org/data/2.5/weather?q=%s&apikey=%s&units=metric"

class RetrofitmplOpenWeather {
        fun getOpenWeatherApi(
        ): OpenWeatherAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(
//                        GsonBuilder().setLenient().create()
                    )
                )
                .client(createOkHttpClient(CommonInterceptor()))
                .build()
            return retrofit.create(OpenWeatherAPI::class.java)
        }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class CommonInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}