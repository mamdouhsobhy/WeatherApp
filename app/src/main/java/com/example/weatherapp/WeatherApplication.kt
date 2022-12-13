package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.core.presentation.common.SharedPrefs
import com.example.weatherapp.core.presentation.utilities.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.onAttach(applicationContext)
        LocaleHelper.setLocale(sharedPrefs.getPreferredLocale())
    }
}