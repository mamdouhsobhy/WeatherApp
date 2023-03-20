package com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.domain.interactor.RoomLocalUseCase
import com.example.weatherapp.weatherActivity.domain.interactor.WeatherUseCase
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel.SearchActivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(private val weatherUseCase: WeatherUseCase,
                    private val roomLocalUseCase: RoomLocalUseCase
) : ViewModel() {
    
    private val _getWeatherState =
        MutableStateFlow<GetWeatherActivityState>(GetWeatherActivityState.Init)
    val getWeatherState: StateFlow<GetWeatherActivityState> get() = _getWeatherState

    private val _getFiveDaysState =
        MutableStateFlow<GetFiveDaysActivityState>(GetFiveDaysActivityState.Init)
    val getFiveDaysState: StateFlow<GetFiveDaysActivityState> get() = _getFiveDaysState

    private fun setLoading() {
        _getWeatherState.value = GetWeatherActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        _getWeatherState.value = GetWeatherActivityState.IsLoading(false)
    }

    private fun showToast(message: String, isConnectionError: Boolean) {
        _getWeatherState.value = GetWeatherActivityState.ShowToast(message, isConnectionError)
    }

    fun getWeather(partMap: Map<String, String>) {
        viewModelScope.launch {
            weatherUseCase.executeGetWeather(partMap)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString(), exception is UnknownHostException)
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> _getWeatherState.value =
                            GetWeatherActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _getWeatherState.value = it.items?.let { it1 ->
                            GetWeatherActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    fun getFiveDays(partMap: Map<String, String>) {
        viewModelScope.launch {
            weatherUseCase.executeGetFiveDays(partMap)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString(), exception is UnknownHostException)
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> _getFiveDaysState.value =
                            GetFiveDaysActivityState.ErrorLogin(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> _getFiveDaysState.value = it.items?.let { it1 ->
                            GetFiveDaysActivityState.Success(
                                it1
                            )
                        }!!
                    }
                }
        }
    }

    suspend fun insertSearch(searchResponse: List<ModelSearchHistory>) {
        roomLocalUseCase.insertSearch(searchResponse)
    }

}

sealed class GetWeatherActivityState {
    object Init : GetWeatherActivityState()
    data class IsLoading(val isLoading: Boolean) : GetWeatherActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : GetWeatherActivityState()
    data class Success(val modelGetWeatherResponseRemote: ModelGetWeatherResponseRemote) :
        GetWeatherActivityState()

    data class ErrorLogin(val errorCode: Int, val errorMessage: String) : GetWeatherActivityState()
}

sealed class GetFiveDaysActivityState {
    object Init : GetFiveDaysActivityState()
    data class IsLoading(val isLoading: Boolean) : GetFiveDaysActivityState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) : GetFiveDaysActivityState()
    data class Success(val modelGetDaysForecastResponse: ModelGetDaysForecastResponseRemote) :
        GetFiveDaysActivityState()

    data class ErrorLogin(val errorCode: Int, val errorMessage: String) : GetFiveDaysActivityState()
}