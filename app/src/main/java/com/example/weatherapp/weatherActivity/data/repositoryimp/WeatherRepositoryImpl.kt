package com.example.weatherapp.weatherActivity.data.repositoryimp

import com.example.weatherapp.weatherActivity.data.datasource.WeatherService
import com.example.weatherapp.weatherActivity.domain.repository.WeatherRepository
import com.example.weatherapp.core.data.utils.WrappedResponse
import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {

    override suspend fun getWeather(partMap: Map<String, String>): Flow<BaseResult<ModelGetWeatherResponseRemote>> {
        return flow {
            val response = weatherService.getCurrentWeather(partMap)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val type = object :
                    TypeToken<ModelGetWeatherResponseRemote>() {}.type
                val err: ModelGetWeatherResponseRemote =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                emit(BaseResult.ErrorState(err.cod, err.message?:"something went wrong"))
            }
        }
    }

    override suspend fun getFiveDays(partMap: Map<String, String>): Flow<BaseResult<ModelGetDaysForecastResponseRemote>> {
        return flow {
            val response = weatherService.getFiveDays(partMap)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val type = object :
                    TypeToken<WrappedResponse<ModelGetDaysForecastResponseRemote>>() {}.type
                val err: WrappedResponse<ModelGetDaysForecastResponseRemote> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.ErrorState(err.code, err.error))
            }
        }
    }

}