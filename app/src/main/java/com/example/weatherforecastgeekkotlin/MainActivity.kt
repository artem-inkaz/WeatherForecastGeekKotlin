package com.example.weatherforecastgeekkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.weatherforecastgeekkotlin.models.DataModel
import com.example.weatherforecastgeekkotlin.models.Fact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weatherforecastgeekkotlin.api.ApiService
import com.example.weatherforecastgeekkotlin.api.Retrofitmpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val retrofitmpl: Retrofitmpl = Retrofitmpl()
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

    private lateinit var current_temperture_field: TextView
    private lateinit var feel_temperature_field: TextView
    private lateinit var condition_text_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        current_temperture_field = findViewById(R.id.current_temperature_field)
        feel_temperature_field = findViewById(R.id.feel_temperature_field)
        condition_text_view = findViewById(R.id.condition_text_view)
        // отправка запроса на сервер
        sendServerRequest()
    }

    private fun sendServerRequest() {
        scope.launch {
        retrofitmpl.getWeatherApi()
//                .subscribeOn(Schedulers.io()) //Подписаться
//                .observeOn(AndroidSchedulers.mainThread()) //Наблюдать
//                .subscribe(
//                        {
//        scope.launch {
            .getWeather(
                token = "bf3d16a4-e51f-4c90-b9bd-23bb1f52ffc7",
                lat = 55.833333,
                lon = 37.616667,
                extra = false
        )
                .enqueue(object :
                        Callback<DataModel> {

                    override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                        // --------------------------------

                      scope.launch {

                            // ----------------------------
                            if (response.isSuccessful && response.body() != null) {
                                renderData(response.body(), null)
                            } else {
                                renderData(null, Throwable(" Ответ от сервера пустой"))
                            }
                       }

                   }

                    override fun onFailure(call: Call<DataModel>, t: Throwable) {
                        renderData(null, t)
                    }
                })
//                        }
//                        {
//                            Log.e(it.message.toString(), "Error get currency")
//                        }
//                )
//                .also (disposable)
       }
    }

    private fun renderData(dataModel: DataModel?, error: Throwable?){
        if (dataModel == null || dataModel.fact == null || error != null){
            Toast.makeText(this,error?.message, Toast.LENGTH_LONG).show()
        } else {
            val fact : Fact = dataModel.fact
            val temperature : Int? = fact.temp
            if (temperature == null) {

            } else {
                current_temperture_field.text = temperature.toString()
            }

            val feelsLike: Int? = fact.feels_like
            if (feelsLike == null) {

            } else {
                feel_temperature_field.text = feelsLike.toString()
            }

            val condition: String? = fact.condition
            if (condition.isNullOrEmpty()){

            } else {
                when (condition){
                    "clear" -> condition_text_view.text = "Ясно"
                    "partly-cloud" -> condition_text_view.text = "Редкие облака"
                    "cloudy" -> condition_text_view.text = "Облачно"
                    "overcast" -> condition_text_view.text = "Пасмурно"
                    "drizzle" -> condition_text_view.text = "морось"
                    "light-rain" -> condition_text_view.text = "Небольшой дождь"
                    "rain" -> condition_text_view.text = "Дождь"
                    "moderate-rain" -> condition_text_view.text = "Умеренно сильный дождь"
                    "heavy-rain" -> condition_text_view.text = "Сильный дождь"
                    "continuous-heavy-rain" -> condition_text_view.text = "Длительный сильный дождь"
                    "showers" -> condition_text_view.text = "Ливень"
                    "snow-showers" -> condition_text_view.text = "Снегопад"
                    "wet-snow" -> condition_text_view.text = "Дождь со снегом"
                    "light-snow" -> condition_text_view.text = "Небольшой снег"
                    "snow" -> condition_text_view.text = "Снег"
                    "hail" -> condition_text_view.text = "Град"
                    "thunderstorm" -> condition_text_view.text = "Гроза"
                    "thunderstorm-with-rain" -> condition_text_view.text = "Дождь с грозой"
                    "thunderstorm-with-hail" -> condition_text_view.text = "Гроза с градом"
                    "partly-cloud-and-light-rain" -> condition_text_view.text = "Редкие облака и легкий дождь"
                    "cloudy-and-light-rain" -> condition_text_view.text = "Облачно и легкий дождь"
                    "overcast-and-light-rain" -> condition_text_view.text = "Пасмурно и легкий дождь"
                    "partly-cloud-and-rain" -> condition_text_view.text = "Редкие облака и дождь"
                    "overcast-and-rain" -> condition_text_view.text = "Пасмурно и дождь"
                    "cloudy-and-rain" -> condition_text_view.text = "Облачно и дождь"
                    "overcast-thunderstorms-with-rain" -> condition_text_view.text = "Пасмурно, гром и дождь"
                    "overcast-and-wet-snow" -> condition_text_view.text = "редкие облака и снег"
                    "partly-cloud-and-light-snow" -> condition_text_view.text = "Редкие облака и легкий снег"
                    "partly-cloud-and-snow" -> condition_text_view.text = "Редкие облака и снег"
                    "overcast-and-snow" -> condition_text_view.text = "Пасмурно и снег"
                    "cloudy-and-light-snow" -> condition_text_view.text = "Облачно и легкий снег"
                    "overcast-and-light-snow" -> condition_text_view.text = "Пасмурно и легкий снег"
                    "cloudy-and-snow" -> condition_text_view.text = "Облачно и снег"
                    else -> condition_text_view.text = "Ясно"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        disposable.dispose()
        scope.cancel()
    }
}