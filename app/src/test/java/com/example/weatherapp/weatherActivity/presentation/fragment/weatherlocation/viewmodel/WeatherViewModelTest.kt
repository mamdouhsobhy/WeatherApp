package com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.datasource.WeatherService
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import com.example.weatherapp.weatherActivity.domain.interactor.RoomLocalUseCase
import com.example.weatherapp.weatherActivity.domain.interactor.WeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var service: WeatherService

    @Mock
    private lateinit var weatherUseCase: WeatherUseCase

    @Mock
    private lateinit var roomLocalUseCase: RoomLocalUseCase

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var observer: Observer<GetWeatherActivityState.Init>

    @Captor
    private lateinit var captor: ArgumentCaptor<GetWeatherActivityState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherViewModel(weatherUseCase, roomLocalUseCase)
    }

    @Test
    fun `getWeather() and  update stateFlow correctly when data return success`() {
        testCoroutineRule.runBlockingTest {
            // Given
            val modelGetWeatherResponseRemote = ModelGetWeatherResponseRemote(
                "", null, 0, null, 0, 0, null, "", null, 0, 0,
                emptyList(), null, ""
            )
            val mockResult = BaseResult.DataState(modelGetWeatherResponseRemote)
            val partMap = mapOf("q" to "giza")

            Mockito.`when`(weatherUseCase.executeGetWeather(partMap)).thenAnswer {
                mockResult
            }

            viewModel.getWeather(partMap)
            assertNotNull(viewModel.getWeatherState.value)

        }

    }

    @Test
    fun `getFiveDaysForecast() and  update stateFlow correctly when data return success`() {
        testCoroutineRule.runBlockingTest {
            // Given
            val modelGetDaysForecastResponseRemote=ModelGetDaysForecastResponseRemote(null,0,0,
                emptyList(),""
            )
            val mockResult = BaseResult.DataState(modelGetDaysForecastResponseRemote)
            val partMap = mapOf("q" to "giza")

            Mockito.`when`(weatherUseCase.executeGetFiveDays(partMap)).thenAnswer {
                mockResult
            }

            viewModel.getFiveDays(partMap)
            assertNotNull(viewModel.getFiveDaysState.value)

        }

    }
}