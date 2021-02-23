package com.example.weatherforecastgeekkotlin.models

data class DataModel(
    val fact: Fact?
)
// совпадают с Gson Яндекс. Погода
data class Fact(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?
)