package com.example.weatherapp.splachActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.weatherapp.core.presentation.base.BaseActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.weatherActivity.presentation.WeatherActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, WeatherActivity::class.java)
                startActivity(intent)
                finish()
        }, SPLASH_TIME_OUT)

    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}