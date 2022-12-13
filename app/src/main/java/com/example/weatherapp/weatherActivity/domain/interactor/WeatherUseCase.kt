package com.example.weatherapp.weatherActivity.domain.interactor
import com.example.weatherapp.weatherActivity.domain.repository.WeatherRepository
import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun executeGetWeather(partMap: Map<String, String>): Flow<BaseResult<ModelGetWeatherResponseRemote>>{
        return weatherRepository.getWeather(partMap)
    }
    suspend fun executeGetFiveDays(partMap: Map<String, String>): Flow<BaseResult<ModelGetDaysForecastResponseRemote>>{
        return weatherRepository.getFiveDays(partMap)
    }
}