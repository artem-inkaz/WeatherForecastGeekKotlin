package com.example.weatherforecastgeekkotlin.openweathermap

import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

internal object ForecastLoader {
    private const val OPEN_WEATHER_MAP_API =
        "http://api.openweathermap.org/data/2.5/weather?q=%s&apikey=%s&units=metric"

    // http://api.openweathermap.org/data/2.5/forecast?id=%s&appid=apikey=%s
    // http://api.openweathermap.org/data/2.5/forecast?id=524901&appid={API key}
    // https://home.openweathermap.org/api_keys
    private const val API_KEY = "9843cafb088167b60fc8403c09a5ecec"
    private const val RESPONSE = "cod"
    private const val NEW_LINE_ = "\n"
    private const val RESPONSE_SUCCESS = 200
    fun getJsonData(city: String?): JSONObject? {
        return try {
            val url = URL(String.format(OPEN_WEATHER_MAP_API, city, API_KEY))
            val connection = url.openConnection() as HttpURLConnection
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val rawData = StringBuilder(1024)
            var tempVariable: String?
            while (reader.readLine().also { tempVariable = it } != null) {
                rawData.append(tempVariable).append(NEW_LINE_)
            }
            reader.close()
            val jsonObject = JSONObject(rawData.toString())
            if (jsonObject.getInt(RESPONSE) == RESPONSE_SUCCESS) {
                jsonObject
            } else {
                null
            }
        } catch (e: IOException) {
            null
        } catch (e: JSONException) {
            null
        }
    }
}