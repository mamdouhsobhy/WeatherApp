package com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.utilities.AppUtil
import com.example.weatherapp.core.presentation.base.BaseFragmentBinding
import com.example.weatherapp.core.presentation.common.SharedPrefs
import com.example.weatherapp.core.presentation.extensions.showGenericAlertDialog
import com.example.weatherapp.core.presentation.extensions.showToast
import com.example.weatherapp.core.presentation.utilities.LocaleHelper
import com.example.weatherapp.core.presentation.utilities.Nav
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.splachActivity.MainActivity
import com.example.weatherapp.weatherActivity.data.responseremote.getWeather.ModelGetWeatherResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.getfivedays.ModelGetDaysForecastResponseRemote
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel.SearchActivityState
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel.SearchViewModel
import com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.adapter.AdapterForecastDays
import com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.bottomsheet.SettingBottomSheet
import com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.viewmodel.GetFiveDaysActivityState
import com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.viewmodel.GetWeatherActivityState
import com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class WeatherFragment : BaseFragmentBinding<FragmentWeatherBinding>() {
    private val args: WeatherFragmentArgs by navArgs()

    private val weatherViewModel by viewModels<WeatherViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    private val adapterForecastDays: AdapterForecastDays by lazy { AdapterForecastDays() }

    private val mMap = HashMap<String, String>()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    var unit = ""
    var searchId = 0
    private var searchList = ArrayList<ModelSearchHistory>()

    lateinit var animationSlideIn: Animation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimation()
        setupViewAnimation()
        setUpDaysForecast()
        addListenerOnView()
        observeStateFlow()
        getSearchHistory()
        getWeather()
    }

    private fun getSearchHistory() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.getSearch()
        }
    }

    private fun handleStateSearch(state: SearchActivityState) {
        when (state) {
            is SearchActivityState.Init -> Unit
            is SearchActivityState.Success -> handleSuccessLocalHistory(state.modelSearchHistory)
        }
    }

    @SuppressLint("NewApi")
    private fun handleSuccessLocalHistory(modelList: List<ModelSearchHistory>) {
        searchList = modelList as ArrayList<ModelSearchHistory>
    }

    private fun addListenerOnView() {
        binding.imgSetting.setOnClickListener {
            showSettings()
        }
        binding.settingBtn.setOnClickListener {
            showSettings()
        }
        binding.swipeRefresh.setOnRefreshListener {
            getWeather()
        }
    }

    private fun showSettings() {
        SettingBottomSheet(itemLanguageAction = {
            sharedPrefs.saveLang(it)
            updateAppLocale(it)
            requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }, itemUnitAction = {
            sharedPrefs.saveUnit(it)
            unit = it
            mMap.clear()
            getWeather()
        }).show(parentFragmentManager, "dialog actions")
    }

    private fun initAnimation() {
        animationSlideIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
    }

    private fun setUpDaysForecast() {
        binding.contentMainLayout.rvDaysForecast.adapter = adapterForecastDays
    }

    private fun getWeather() {
        mMap.clear()
        if (unit.isNotEmpty()) {
            mMap["units"] = unit
        } else {
            if (sharedPrefs.getUnit().isEmpty()) {
                mMap["units"] = Nav.DefaultUnit.unit
            } else {
                mMap["units"] = sharedPrefs.getUnit()
            }
        }
        if (sharedPrefs.getLang().isEmpty()) {
            mMap["lang"] = Nav.DefaultLang.lang
        } else {
            mMap["lang"] = sharedPrefs.getLang()
        }

        mMap["q"] = args.location
        mMap["appid"] = Nav.ApiKey.API_KEY
        weatherViewModel.getWeather(mMap)
        weatherViewModel.getFiveDays(mMap)
    }

    private fun observeStateFlow() {
        weatherViewModel.getWeatherState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        weatherViewModel.getFiveDaysState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChangeFiveDays(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        searchViewModel.searchState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateSearch(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun handleStateChange(state: GetWeatherActivityState) {
        when (state) {
            is GetWeatherActivityState.Init -> Unit
            is GetWeatherActivityState.ErrorLogin -> handleErrorLogin(state.errorCode,
                state.errorMessage
            )
            is GetWeatherActivityState.Success -> handleSuccess(state.modelGetWeatherResponseRemote)
            is GetWeatherActivityState.ShowToast -> requireActivity().showToast(
                state.message, state.isConnectionError
            )
            is GetWeatherActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleStateChangeFiveDays(state: GetFiveDaysActivityState) {
        when (state) {
            is GetFiveDaysActivityState.Init -> Unit
            is GetFiveDaysActivityState.ErrorLogin -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is GetFiveDaysActivityState.Success -> handleSuccessFiveDays(state.modelGetDaysForecastResponse)
            is GetFiveDaysActivityState.ShowToast -> requireActivity().showToast(
                state.message, state.isConnectionError
            )
            is GetFiveDaysActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleErrorLogin(code : Int, errorMessage: String) {
        requireActivity().showGenericAlertDialog(errorMessage)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.swipeRefresh.isRefreshing = isLoading
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccess(weatherResponse: ModelGetWeatherResponseRemote) {
        binding.contentMainLayout.tvCurrentLocation.text =
            weatherResponse.name + " " + weatherResponse.sys.country
        binding.contentMainLayout.tvTemprature.text = java.lang.String.format(
            Locale.getDefault(), "%.0f°", weatherResponse.main.temp
        )
        binding.contentMainLayout.tvDescriptionTemp.text = weatherResponse.weather[0].description

        binding.contentMainLayout.animationView.setAnimation(
            AppUtil.getWeatherAnimation(
                weatherResponse.weather[0].id
            )
        )
        binding.contentMainLayout.animationView.playAnimation()

        lifecycleScope.launch {
            var found = false
            for (item in searchList) {
                if (item.searchName == args.location) {
                    found = true
                }
            }
            if (!found) {
                if (sharedPrefs.getId().isEmpty()) {
                    sharedPrefs.saveId("1")
                } else {
                    searchId = (sharedPrefs.getId()).toInt()
                    searchId += 1
                    sharedPrefs.saveId("$searchId")
                }
                searchList.add(ModelSearchHistory(id = searchId, searchName = args.location))

                weatherViewModel.insertSearch(searchList)
            }

        }

    }

    private fun handleSuccessFiveDays(weatherResponse: ModelGetDaysForecastResponseRemote) {
        adapterForecastDays.submitList(weatherResponse.list)
    }

    private fun setupViewAnimation() {
        binding.contentMainLayout.tvTemprature.startAnimation(animationSlideIn)
        binding.contentMainLayout.tvDescriptionTemp.startAnimation(animationSlideIn)
    }

    private fun updateAppLocale(locale: String) {
        sharedPrefs.setPreferredLocale(locale)
        LocaleHelper.setLocale(locale)
    }


}