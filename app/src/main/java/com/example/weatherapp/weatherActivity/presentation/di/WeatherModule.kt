package com.example.weatherapp.weatherActivity.presentation.di
import com.example.weatherapp.core.data.module.NetworkModule
import com.example.weatherapp.weatherActivity.data.datasource.WeatherService
import com.example.weatherapp.weatherActivity.data.repositoryimp.WeatherRepositoryImpl
import com.example.weatherapp.weatherActivity.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class WeatherModule {

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(WeatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(WeatherService)
    }

}