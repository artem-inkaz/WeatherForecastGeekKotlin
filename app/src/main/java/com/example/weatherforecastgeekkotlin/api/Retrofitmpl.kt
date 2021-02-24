package com.example.weatherforecastgeekkotlin.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class Retrofitmpl {
        fun getWeatherApi(
//            interceptor: CommonInterceptor,
//            loggingInterceptor: LoggingInterceptor
        ): WeatherAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.weather.yandex.ru/")
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(
//                        GsonBuilder().setLenient().create()
                    )
                )
                .client(createOkHttpClient(CommonInterceptor()))
                .build()
            return retrofit.create(WeatherAPI::class.java)
        }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class CommonInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
                .newBuilder()
                .addHeader("X-Yandex-API-Key",  "bf3d16a4-e51f-4c90-b9bd-23bb1f52ffc7")
                .build()
            return chain.proceed(request)
        }
    }
}