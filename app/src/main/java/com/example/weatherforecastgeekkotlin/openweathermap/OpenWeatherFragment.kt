package com.example.weatherforecastgeekkotlin.openweathermap

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherforecastgeekkotlin.R
import com.example.weatherforecastgeekkotlin.api2.Constants
import com.example.weatherforecastgeekkotlin.openweathermap.api.RetrofitmplOpenWeather
import com.example.weatherforecastgeekkotlin.openweathermap.models.DataModelMap
import com.example.weatherforecastgeekkotlin.openweathermap.models.Json4Kotlin_Base
import com.example.weatherforecastgeekkotlin.openweathermap.models.Main
import kotlinx.android.synthetic.main.fragment_open_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class OpenWeatherFragment : Fragment(R.layout.fragment_open_weather) {

    private var appCash: AppCash? = null
    private var cityTextView: TextView? = null
    private var updatedTextView: TextView? = null
    private var detailsTextView: TextView? = null
    private var currentTemperatureTextView: TextView? = null
    private var weatherIconTextView: TextView? = null

    private var buttonselect: Button? = null
    private val retrofitmplOpenWeather: RetrofitmplOpenWeather = RetrofitmplOpenWeather()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_open_weather, container, false)
//        setViews()
//        appCash = AppCash(requireActivity())
//        appCash?.saveCity(city = String())
//        button_select.setOnClickListener {
//            showInputDialog()
//            Toast.makeText(requireContext(),"Клик", Toast.LENGTH_LONG).show()
//
//        }
//        return view

//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        appCash = AppCash(requireActivity())
        appCash?.saveCity(city = String())

        button_select.setOnClickListener {
            showInputDialog()
            Toast.makeText(requireContext(),"Клик", Toast.LENGTH_LONG).show()

        }
    }

    private fun setViews() {
        buttonselect = view?.findViewById(R.id.button_select)!!
        cityTextView = view?.findViewById(R.id.city_field)
        updatedTextView = view?.findViewById(R.id.update_field)
        detailsTextView = view?.findViewById(R.id.details_field)
        currentTemperatureTextView = view?.findViewById(R.id.current_temperature_field)
        weatherIconTextView = view?.findViewById(R.id.weather_icon)
//        weatherIcon.setTypeface(weatherFont)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.weather, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_city) {
            showInputDialog()
            return true
        }
        return false
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.change_city_dialog))
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton(R.string.go, DialogInterface.OnClickListener { dialog, which ->
            changeCity(
                input.text.toString()
            )
        })
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    fun changeCity(city: String) {

//        startThread()
//        updateWeatherData(city)
        sendServerRequest(city)
        appCash!!.saveCity(city)

    }

    private fun sendServerRequest(city: String) {
//        scope.launch {
      val url = URL(String.format(Constants.OPEN_WEATHER_MAP_API, city, Constants.API_KEY1))
        retrofitmplOpenWeather.getOpenWeatherApi()

            .getOpenWeather(
                q = city,
                token = "9843cafb088167b60fc8403c09a5ecec",
                units = "metric",
//                metric = ""

//                "units=metric"

            )
            // возвращаем модель данных
            .enqueue(object :
                Callback<DataModelMap> {
                override fun onResponse(
                    call: Call<DataModelMap>,
                    response: Response<DataModelMap>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        renderData(response.body()!!, Throwable(" Данные есть! Они идут"))
                    } else {
                        renderData(null, Throwable(" Ответ от сервера пустой"))
                    }
                }

                override fun onFailure(call: Call<DataModelMap>, t: Throwable) {
                    renderData(null, t)
                }
            })
//        }
    }

    private fun renderData(dataModel: DataModelMap?, error: Throwable?) {
        if (dataModel?.main == null || error == null){
            Toast.makeText(requireContext(),error?.message, Toast.LENGTH_LONG).show()
        } else {
            cityTextView?.text = dataModel.name
            val main : Main? = dataModel?.main
            val temperature : Double? = main?.temp
            if (temperature == null) {

            } else {
//                findViewById<TextView>(R.id.current_temperature_field).text = fact?.temp.toString()
                weatherIconTextView?.text = temperature.toString()
            }

            val feelsLike: Double? = main?.feels_like
            if (feelsLike == null) {

            } else {
                detailsTextView?.text = feelsLike.toString()
            }

//            val condition: String? = fact?.condition
//            if (condition.isNullOrEmpty()){
//
//            } else {
//                when (condition){
//                    "clear" -> conditiontextview.text = "Ясно"
//                    "partly-cloud" -> conditiontextview.text = "Редкие облака"
//                    "cloudy" -> conditiontextview.text = "Облачно"
//                    "overcast" -> conditiontextview.text = "Пасмурно"
//                    "drizzle" -> conditiontextview.text = "морось"
//                    "light-rain" -> conditiontextview.text = "Небольшой дождь"
//                    "rain" -> conditiontextview.text = "Дождь"
//                    "moderate-rain" -> conditiontextview.text = "Умеренно сильный дождь"
//                    "heavy-rain" -> conditiontextview.text = "Сильный дождь"
//                    "continuous-heavy-rain" -> conditiontextview.text = "Длительный сильный дождь"
//                    "showers" -> conditiontextview.text = "Ливень"
//                    "snow-showers" -> conditiontextview.text = "Снегопад"
//                    "wet-snow" -> conditiontextview.text = "Дождь со снегом"
//                    "light-snow" -> conditiontextview.text = "Небольшой снег"
//                    "snow" -> conditiontextview.text = "Снег"
//                    "hail" -> conditiontextview.text = "Град"
//                    "thunderstorm" -> conditiontextview.text = "Гроза"
//                    "thunderstorm-with-rain" -> conditiontextview.text = "Дождь с грозой"
//                    "thunderstorm-with-hail" -> conditiontextview.text = "Гроза с градом"
//                    "partly-cloud-and-light-rain" -> conditiontextview.text = "Редкие облака и легкий дождь"
//                    "cloudy-and-light-rain" -> conditiontextview.text = "Облачно и легкий дождь"
//                    "overcast-and-light-rain" -> conditiontextview.text = "Пасмурно и легкий дождь"
//                    "partly-cloud-and-rain" -> conditiontextview.text = "Редкие облака и дождь"
//                    "overcast-and-rain" -> conditiontextview.text = "Пасмурно и дождь"
//                    "cloudy-and-rain" -> conditiontextview.text = "Облачно и дождь"
//                    "overcast-thunderstorms-with-rain" -> conditiontextview.text = "Пасмурно, гром и дождь"
//                    "overcast-and-wet-snow" -> conditiontextview.text = "редкие облака и снег"
//                    "partly-cloud-and-light-snow" -> conditiontextview.text = "Редкие облака и легкий снег"
//                    "partly-cloud-and-snow" -> conditiontextview.text = "Редкие облака и снег"
//                    "overcast-and-snow" -> conditiontextview.text = "Пасмурно и снег"
//                    "cloudy-and-light-snow" -> conditiontextview.text = "Облачно и легкий снег"
//                    "overcast-and-light-snow" -> conditiontextview.text = "Пасмурно и легкий снег"
//                    "cloudy-and-snow" -> conditiontextview.text = "Облачно и снег"
//                    else -> conditiontextview.text = "Ясно"
//                }
//            }
        }

    }

    companion object {
        fun neInstance(): OpenWeatherFragment{
            return OpenWeatherFragment()
        }
    }

}