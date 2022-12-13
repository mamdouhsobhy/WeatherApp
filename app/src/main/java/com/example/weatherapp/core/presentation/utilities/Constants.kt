package com.example.weatherapp.core.presentation.utilities

object Constants {
    val WEATHER_STATUS = arrayOf(
        "Thunderstorm",
        "Drizzle",
        "Rain",
        "Snow",
        "Atmosphere",
        "Clear",
        "Few Clouds",
        "Broken Clouds",
        "Cloud"
    )
    val WEATHER_STATUS_PERSIAN = arrayOf(
        "رعد و برق",
        "نمنم باران",
        "باران",
        "برف",
        "جو ناپایدار",
        "صاف",
        "کمی ابری",
        "ابرهای پراکنده",
        "ابری"
    )
    const val TIME_TO_PASS = (6 * 600000).toLong()

}