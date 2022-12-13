package com.example.weatherapp.weatherActivity.data.responseremote.getfivedays

data class ModelGetDaysForecastResponseRemote(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<FiveDays>,
    val message: Int
)