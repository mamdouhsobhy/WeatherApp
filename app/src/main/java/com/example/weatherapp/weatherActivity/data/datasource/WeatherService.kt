package com.example.weatherapp.weatherActivity.data.datasource
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import retrofit2.Response
import retrofit2.http.*

interface WeatherService {
    
    @GET("weather")
    suspend fun getCurrentWeather(@QueryMap partMap: Map<String, String>): Response<ModelGetWeatherResponseRemote>

    @GET("forecast")
    suspend fun getFiveDays(@QueryMap partMap: Map<String, String>): Response<ModelGetDaysForecastResponseRemote>

}