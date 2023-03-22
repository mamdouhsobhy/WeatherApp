package com.example.weatherapp.weatherActivity.data.repositoryimp

import com.example.weatherapp.core.presentation.base.BaseResult
import com.example.weatherapp.weatherActivity.data.datasource.WeatherService
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepositoryImplTest {

    /*
      * Repository Implementation getWeather() when response is successfully  code = 200
      * Repository Implementation getWeather() when response is failure in case location not send  code = 400
      * Repository Implementation getWeather() when response is failure in case appId not send  code = 401
      * Repository Implementation getWeather() when response is failure in case path not send success  code = 404
      *
      *
      * Repository Implementation getFiveDaysForecast() when response is successfully  code = 200
      * Repository Implementation getFiveDaysForecast() when response is failure in case location not send  code = 400
      * Repository Implementation getFiveDaysForecast() when response is failure in case appId not send  code = 401
      * Repository Implementation getFiveDaysForecast() when response is failure in case path not send success  code = 404
     */


    private lateinit var service: WeatherService
    private lateinit var server: MockWebServer

    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl

    private lateinit var modelGetWeather: ModelGetWeatherResponseRemote
    private lateinit var modelGetFiveDaysForecast: ModelGetDaysForecastResponseRemote
    var errorCode:Int?=0
    var errorMessage:String?=""

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))//We will use MockWebServers url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        weatherRepositoryImpl = WeatherRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(
        fileName: String,
        responseCode:Int
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(responseCode)
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun `Test Repository Implementation getWeather() when response is successfully code = 200`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherResponse.json",200)

            val partMap = mapOf(
                "q" to "qena",
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getWeather(partMap).onStart {

            }.catch { exception ->
                }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {

                        }
                        is BaseResult.DataState -> {
                            modelGetWeather = it.items!!
                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/weather?q=qena&appid=7f55b7159566170c7fd7e26f4c3571bf")
        assertThat(modelGetWeather.cod).isEqualTo(200)
        assertThat(modelGetWeather.name).isEqualTo("Qena")
        assertThat(modelGetWeather.sys?.country).isEqualTo("EG")
        assertThat(modelGetWeather.main?.temp).isEqualTo(22.62)
        assertThat(modelGetWeather.weather[0].id).isEqualTo(800)
        assertThat(modelGetWeather.weather[0].description).isEqualTo("clear sky")
    }

    @Test
    fun `Test Repository Implementation getWeather() when response is failure in case location not send  code = 400`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorLocationResponse.json",400)

            val partMap = mapOf(
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getWeather(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/weather?appid=7f55b7159566170c7fd7e26f4c3571bf")
        assertThat(errorCode).isEqualTo(400)
        assertThat(errorMessage).isEqualTo("Nothing to geocode")

    }

    @Test
    fun `Test Repository Implementation getWeather() when response is failure in case appId not send  code = 401`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorAppIdResponse.json",401)

            val partMap = mapOf(
                "q" to "qena"
            )
            weatherRepositoryImpl.getWeather(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/weather?q=qena")
        assertThat(errorCode).isEqualTo(401)
        assertThat(errorMessage).isEqualTo("Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.")

    }

    @Test
    fun `Test Repository Implementation getWeather() when response is failure in case path not send success code = 404`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorApiResponse.json",404)

            val partMap = mapOf(
                "q" to "qena",
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getWeather(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(errorCode).isEqualTo(404)
        assertThat(errorMessage).isEqualTo("Internal error")

    }

    @Test
    fun `Test Repository Implementation getFiveDaysForecast() when response is successfully code = 200`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetFiveDaysForecastResponse.json",200)

            val partMap = mapOf(
                "q" to "qena",
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getFiveDays(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {

                        }
                        is BaseResult.DataState -> {
                            modelGetFiveDaysForecast = it.items!!
                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/forecast?q=qena&appid=7f55b7159566170c7fd7e26f4c3571bf")
        assertThat(modelGetFiveDaysForecast.cod).isEqualTo(200)
        assertThat(modelGetFiveDaysForecast.city?.name).isEqualTo("Qena")
        assertThat(modelGetFiveDaysForecast.city?.country).isEqualTo("EG")
        assertThat(modelGetFiveDaysForecast.list[0].main.temp).isEqualTo(24.3)
        assertThat(modelGetFiveDaysForecast.list[0].weather[0].id).isEqualTo(800)
        assertThat(modelGetFiveDaysForecast.list[0].weather[0].description).isEqualTo("clear sky")
    }

    @Test
    fun `Test Repository Implementation getFiveDaysForecast() when response is failure in case location not send  code = 400`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorLocationResponse.json",400)

            val partMap = mapOf(
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getFiveDays(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/forecast?appid=7f55b7159566170c7fd7e26f4c3571bf")
        assertThat(errorCode).isEqualTo(400)
        assertThat(errorMessage).isEqualTo("Nothing to geocode")

    }

    @Test
    fun `Test Repository Implementation getFiveDaysForecast() when response is failure in case appId not send  code = 401`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorAppIdResponse.json",401)

            val partMap = mapOf(
                "q" to "qena"
            )
            weatherRepositoryImpl.getFiveDays(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/forecast?q=qena")
        assertThat(errorCode).isEqualTo(401)
        assertThat(errorMessage).isEqualTo("Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.")

    }

    @Test
    fun `Test Repository Implementation getFiveDaysForecast() when response is failure in case path not send success code = 404`() {
        runBlocking {

            // Prepare fake response
            enqueueMockResponse("GetWeatherErrorApiResponse.json",404)

            val partMap = mapOf(
                "q" to "qena",
                "appid" to "7f55b7159566170c7fd7e26f4c3571bf"
            )
            weatherRepositoryImpl.getFiveDays(partMap).onStart {

            }.catch { exception ->
            }
                .collect {
                    when (it) {
                        is BaseResult.ErrorState -> {
                            errorCode = it.errorCode
                            errorMessage = it.errorMessage
                        }
                        is BaseResult.DataState -> {

                        }
                    }
                }
        }
        val request = server.takeRequest()

        assertThat(request.method).isEqualTo("GET")
        assertThat(errorCode).isEqualTo(404)
        assertThat(errorMessage).isEqualTo("Internal error")

    }

}