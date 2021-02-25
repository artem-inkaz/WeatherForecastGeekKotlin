package com.example.weatherforecastgeekkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.weatherforecastgeekkotlin.models.DataModel
import com.example.weatherforecastgeekkotlin.models.Fact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weatherforecastgeekkotlin.api.Retrofitmpl
import com.example.weatherforecastgeekkotlin.apigeokoder.RetrofitmplGeokoder
import com.example.weatherforecastgeekkotlin.models.DataModelGeo
import com.example.weatherforecastgeekkotlin.openweathermap.OpenWeatherActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val retrofitmpl: Retrofitmpl = Retrofitmpl()
    private val retrofitmplGeokoder: RetrofitmplGeokoder = RetrofitmplGeokoder()
//    private val apiService = ApiService.getWeatherApi()
    private val disposable = CompositeDisposable()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(this.toString(),"CoroutineException: $exception")
    }

    private var scope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.IO +
                    exceptionHandler
    )

    private lateinit var currenttemperturefield: TextView
    private lateinit var feeltemperaturefield: TextView
    private lateinit var conditiontextview: TextView

    private lateinit var button_openweather: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currenttemperturefield = findViewById(R.id.current_temperature_field)
        feeltemperaturefield = findViewById(R.id.feel_temperature_field)
        conditiontextview = findViewById(R.id.condition_text_view)
        // отправка запроса на сервер
        sendServerRequest()
//        sendServerRequestGeokoder()

        button_openweather = findViewById(R.id.button_openweather)
        button_openweather.setOnClickListener {
            // Variant 1
            val i = Intent(this,OpenWeatherActivity::class.java).apply { }
            startActivity(i)
            // Variant 2
//              val intent2 = newIntent(this@MainActivity, false)
//              startActivity(intent2)
        }
    }
    // отправка запроса погоды по координатам на сервер
    private fun sendServerRequest() {
        scope.launch {
            retrofitmpl.getWeatherApi()
                .getWeather(
//                    token = "bf3d16a4-e51f-4c90-b9bd-23bb1f52ffc7",
                    lat = 55.833333,
                    lon = 37.616667,
                    extra = true
                )
                // возвращаем модель данных
                .enqueue(object :
                    Callback<DataModel> {
                    override fun onResponse(
                        call: Call<DataModel>,
                        response: Response<DataModel>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            renderData(response.body()!!, Throwable(" ${currenttemperturefield.text}"))
                        } else {
                            renderData(null, Throwable(" Ответ от сервера пустой"))
                        }
                    }

                    override fun onFailure(call: Call<DataModel>, t: Throwable) {
                        renderData(null, t)
                    }
                })
      }
    }
    // погода по координатам
    private fun renderData(dataModel: DataModel?, error: Throwable?) {
            if (dataModel?.fact == null || error == null){
                Toast.makeText(this,error?.message, Toast.LENGTH_LONG).show()
            } else {
                val fact : Fact? = dataModel?.fact
                val temperature : Int? = fact?.temp
                if (temperature == null) {

                } else {
                    findViewById<TextView>(R.id.current_temperature_field).text = fact?.temp.toString()
                    currenttemperturefield.text = temperature.toString()
                }

                val feelsLike: Int? = fact?.feels_like
                if (feelsLike == null) {

                } else {
                    feeltemperaturefield.text = feelsLike.toString()
                }

                val condition: String? = fact?.condition
                if (condition.isNullOrEmpty()){

                } else {
                    when (condition){
                        "clear" -> conditiontextview.text = "Ясно"
                        "partly-cloud" -> conditiontextview.text = "Редкие облака"
                        "cloudy" -> conditiontextview.text = "Облачно"
                        "overcast" -> conditiontextview.text = "Пасмурно"
                        "drizzle" -> conditiontextview.text = "морось"
                        "light-rain" -> conditiontextview.text = "Небольшой дождь"
                        "rain" -> conditiontextview.text = "Дождь"
                        "moderate-rain" -> conditiontextview.text = "Умеренно сильный дождь"
                        "heavy-rain" -> conditiontextview.text = "Сильный дождь"
                        "continuous-heavy-rain" -> conditiontextview.text = "Длительный сильный дождь"
                        "showers" -> conditiontextview.text = "Ливень"
                        "snow-showers" -> conditiontextview.text = "Снегопад"
                        "wet-snow" -> conditiontextview.text = "Дождь со снегом"
                        "light-snow" -> conditiontextview.text = "Небольшой снег"
                        "snow" -> conditiontextview.text = "Снег"
                        "hail" -> conditiontextview.text = "Град"
                        "thunderstorm" -> conditiontextview.text = "Гроза"
                        "thunderstorm-with-rain" -> conditiontextview.text = "Дождь с грозой"
                        "thunderstorm-with-hail" -> conditiontextview.text = "Гроза с градом"
                        "partly-cloud-and-light-rain" -> conditiontextview.text = "Редкие облака и легкий дождь"
                        "cloudy-and-light-rain" -> conditiontextview.text = "Облачно и легкий дождь"
                        "overcast-and-light-rain" -> conditiontextview.text = "Пасмурно и легкий дождь"
                        "partly-cloud-and-rain" -> conditiontextview.text = "Редкие облака и дождь"
                        "overcast-and-rain" -> conditiontextview.text = "Пасмурно и дождь"
                        "cloudy-and-rain" -> conditiontextview.text = "Облачно и дождь"
                        "overcast-thunderstorms-with-rain" -> conditiontextview.text = "Пасмурно, гром и дождь"
                        "overcast-and-wet-snow" -> conditiontextview.text = "редкие облака и снег"
                        "partly-cloud-and-light-snow" -> conditiontextview.text = "Редкие облака и легкий снег"
                        "partly-cloud-and-snow" -> conditiontextview.text = "Редкие облака и снег"
                        "overcast-and-snow" -> conditiontextview.text = "Пасмурно и снег"
                        "cloudy-and-light-snow" -> conditiontextview.text = "Облачно и легкий снег"
                        "overcast-and-light-snow" -> conditiontextview.text = "Пасмурно и легкий снег"
                        "cloudy-and-snow" -> conditiontextview.text = "Облачно и снег"
                        else -> conditiontextview.text = "Ясно"
                    }
                }
            }

    }

    // отправка запроса название места по координатам на сервер
//    private fun sendServerRequestGeokoder() {
////        scope.launch {
//        retrofitmplGeokoder.getGeokoderApi()
//            .getGeokoder(
//                "bf3d16a4-e51f-4c90-b9bd-23bb1f52ffc7",
//                 "55.833333,37.616667"
//            )
//            // возвращаем модель данных
//            .enqueue(object :
//                Callback<DataModelGeo> {
//                override fun onResponse(
//                    call: Call<DataModelGeo>,
//                    response: Response<DataModelGeo>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        renderDataGeokoder(response.body()!!, Throwable(" ${currenttemperturefield.text}"))
//                    } else {
//                        renderData(null, Throwable(" Ответ от сервера пустой"))
//                    }
//                }
//
//                override fun onFailure(call: Call<DataModelGeo>, t: Throwable) {
//                    renderData(null, t)
//                }
//            })
////        }
//    }
    // погода по координатам
//    private fun renderDataGeokoder(dataModelGeo: DataModelGeo?, error: Throwable?) {
//        if (dataModelGeo?.geocode == null || error == null){
//            Toast.makeText(this,error?.message, Toast.LENGTH_LONG).show()
//        } else {
//            val geocode : String? = dataModelGeo?.geocode
//            if (geocode == null) {
//
//            } else {
//                findViewById<TextView>(R.id.title_text).text = dataModelGeo?.geocode
////                currenttemperturefield.text = temperature.toString()
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
//        disposable.dispose()
        scope.cancel()
    }
}


