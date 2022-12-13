package com.example.weatherapp.weatherActivity.presentation
import android.os.Bundle
import com.example.weatherapp.core.presentation.base.BaseActivity
import com.example.weatherapp.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : BaseActivity<ActivityWeatherBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}