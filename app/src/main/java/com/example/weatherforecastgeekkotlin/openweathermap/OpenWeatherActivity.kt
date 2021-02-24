package com.example.weatherforecastgeekkotlin.openweathermap

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecastgeekkotlin.R
import com.example.weatherforecastgeekkotlin.models.DataModel
import com.example.weatherforecastgeekkotlin.models.Fact
import com.example.weatherforecastgeekkotlin.openweathermap.api.RetrofitmplOpenWeather
import com.example.weatherforecastgeekkotlin.openweathermap.models.DataModelMap
import com.example.weatherforecastgeekkotlin.openweathermap.models.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenWeatherActivity : AppCompatActivity() {
    private var appCash: AppCash? = null
    private var cityTextView: TextView? = null
    private var updatedTextView: TextView? = null
    private var detailsTextView: TextView? = null
    private var currentTemperatureTextView: TextView? = null
    private var weatherIconTextView: TextView? = null

    private val retrofitmplOpenWeather: RetrofitmplOpenWeather = RetrofitmplOpenWeather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_weather)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null){
            // 8.12
//            val fragment = CrimeFragment()
            // 9.4
            val fragment = OpenWeatherFragment.neInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }
//        startThread()
//        appCash = AppCash(this)
        //        weatherFont = Typeface.createFromAsset(getAssets(), FONT_FILENAME);
//        setViews()
//        appCash?.saveCity(city = String())
    }

//    private fun setViews() {
//        cityTextView = findViewById(R.id.city_field)
//        updatedTextView = findViewById(R.id.update_field)
//        detailsTextView = findViewById(R.id.details_field)
//        currentTemperatureTextView = findViewById(R.id.current_temperature_field)
//        weatherIconTextView = findViewById(R.id.weather_icon)
////        weatherIcon.setTypeface(weatherFont)
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.weather, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.change_city) {
//            showInputDialog()
//            return true
//        }
//        return false
//    }
//
//    private fun showInputDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(getString(R.string.change_city_dialog))
//        val input = EditText(this)
//        input.inputType = InputType.TYPE_CLASS_TEXT
//        builder.setView(input)
//        builder.setPositiveButton(R.string.go, DialogInterface.OnClickListener { dialog, which ->
//            changeCity(
//                input.text.toString()
//            )
//        })
//        builder.setNegativeButton("Cancel", null)
//        builder.show()
//    }
//
//    fun changeCity(city: String) {
//
////        startThread()
////        updateWeatherData(city)
//        sendServerRequest(city)
//        appCash!!.saveCity(city)
//
//    }
//
//    private fun sendServerRequest(city: String) {
////        scope.launch {
//        retrofitmplOpenWeather.getOpenWeatherApi()
//            .getOpenWeather(
//                token = "bf3d16a4-e51f-4c90-b9bd-23bb1f52ffc7",
//                name = city,
////                "units=metric"
//
//            )
//            // возвращаем модель данных
//            .enqueue(object :
//                Callback<DataModelMap> {
//                override fun onResponse(
//                    call: Call<DataModelMap>,
//                    response: Response<DataModelMap>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        renderData(response.body()!!, Throwable(" Данные есть! Они идут"))
//                    } else {
//                        renderData(null, Throwable(" Ответ от сервера пустой"))
//                    }
//                }
//
//                override fun onFailure(call: Call<DataModelMap>, t: Throwable) {
//                    renderData(null, t)
//                }
//            })
////        }
//    }
//
//    private fun renderData(dataModel: DataModelMap?, error: Throwable?) {
//        if (dataModel?.main == null || error == null){
//            Toast.makeText(this,error?.message, Toast.LENGTH_LONG).show()
//        } else {
//            cityTextView?.text = " Здесь будет название города"
//            val main : Main? = dataModel?.main
//            val temperature : Int? = main?.temp
//            if (temperature == null) {
//
//            } else {
////                findViewById<TextView>(R.id.current_temperature_field).text = fact?.temp.toString()
//                weatherIconTextView?.text = temperature.toString()
//            }
//
//            val feelsLike: Double? = main?.feels_like
//            if (feelsLike == null) {
//
//            } else {
//                detailsTextView?.text = feelsLike.toString()
//            }
//
////            val condition: String? = fact?.condition
////            if (condition.isNullOrEmpty()){
////
////            } else {
////                when (condition){
////                    "clear" -> conditiontextview.text = "Ясно"
////                    "partly-cloud" -> conditiontextview.text = "Редкие облака"
////                    "cloudy" -> conditiontextview.text = "Облачно"
////                    "overcast" -> conditiontextview.text = "Пасмурно"
////                    "drizzle" -> conditiontextview.text = "морось"
////                    "light-rain" -> conditiontextview.text = "Небольшой дождь"
////                    "rain" -> conditiontextview.text = "Дождь"
////                    "moderate-rain" -> conditiontextview.text = "Умеренно сильный дождь"
////                    "heavy-rain" -> conditiontextview.text = "Сильный дождь"
////                    "continuous-heavy-rain" -> conditiontextview.text = "Длительный сильный дождь"
////                    "showers" -> conditiontextview.text = "Ливень"
////                    "snow-showers" -> conditiontextview.text = "Снегопад"
////                    "wet-snow" -> conditiontextview.text = "Дождь со снегом"
////                    "light-snow" -> conditiontextview.text = "Небольшой снег"
////                    "snow" -> conditiontextview.text = "Снег"
////                    "hail" -> conditiontextview.text = "Град"
////                    "thunderstorm" -> conditiontextview.text = "Гроза"
////                    "thunderstorm-with-rain" -> conditiontextview.text = "Дождь с грозой"
////                    "thunderstorm-with-hail" -> conditiontextview.text = "Гроза с градом"
////                    "partly-cloud-and-light-rain" -> conditiontextview.text = "Редкие облака и легкий дождь"
////                    "cloudy-and-light-rain" -> conditiontextview.text = "Облачно и легкий дождь"
////                    "overcast-and-light-rain" -> conditiontextview.text = "Пасмурно и легкий дождь"
////                    "partly-cloud-and-rain" -> conditiontextview.text = "Редкие облака и дождь"
////                    "overcast-and-rain" -> conditiontextview.text = "Пасмурно и дождь"
////                    "cloudy-and-rain" -> conditiontextview.text = "Облачно и дождь"
////                    "overcast-thunderstorms-with-rain" -> conditiontextview.text = "Пасмурно, гром и дождь"
////                    "overcast-and-wet-snow" -> conditiontextview.text = "редкие облака и снег"
////                    "partly-cloud-and-light-snow" -> conditiontextview.text = "Редкие облака и легкий снег"
////                    "partly-cloud-and-snow" -> conditiontextview.text = "Редкие облака и снег"
////                    "overcast-and-snow" -> conditiontextview.text = "Пасмурно и снег"
////                    "cloudy-and-light-snow" -> conditiontextview.text = "Облачно и легкий снег"
////                    "overcast-and-light-snow" -> conditiontextview.text = "Пасмурно и легкий снег"
////                    "cloudy-and-snow" -> conditiontextview.text = "Облачно и снег"
////                    else -> conditiontextview.text = "Ясно"
////                }
////            }
//        }
//
//    }


}