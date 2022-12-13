package com.example.weatherapp.weatherActivity.domain.repository
import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(partMap: Map<String, String>): Flow<BaseResult<ModelGetWeatherResponseRemote>>
    suspend fun getFiveDays(partMap: Map<String, String>): Flow<BaseResult<ModelGetDaysForecastResponseRemote>>

}