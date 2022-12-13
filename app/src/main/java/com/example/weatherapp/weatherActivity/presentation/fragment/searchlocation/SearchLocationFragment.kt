package com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.base.BaseFragmentBinding
import com.example.weatherapp.core.presentation.utilities.Nav
import com.example.weatherapp.core.presentation.utilities.isEmpty
import com.example.weatherapp.databinding.FragmentSearchLocationBinding
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.adapter.AdapterHistorySearch
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel.SearchActivityState
import com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchLocationFragment : BaseFragmentBinding<FragmentSearchLocationBinding>() {
    private val adapterHistorySearch: AdapterHistorySearch by lazy {
        AdapterHistorySearch(itemSelected = {
            binding.edLocation.setText(it)
        })
    }
    private val searchViewModel by viewModels<SearchViewModel>()
    private var searchList = ArrayList<ModelSearchHistory>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        addListenerOnView()
        getSearchHistory()
        observeStateFlow()

    }

    private fun observeStateFlow() {

        searchViewModel.searchState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateSearch(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun handleStateSearch(state: SearchActivityState) {
        when (state) {
            is SearchActivityState.Init -> Unit
            is SearchActivityState.Success -> handleSuccessLocalHistory(state.modelSearchHistory)
        }
    }

    @SuppressLint("NewApi")
    private fun handleSuccessLocalHistory(modelList: List<ModelSearchHistory>) {
        searchList= modelList as ArrayList<ModelSearchHistory>
        if (modelList.isEmpty()) {
            binding.searchTextView.isVisible = true
            binding.layoutSearchHistory.isVisible = false
        } else {
            binding.searchTextView.isVisible = false
            binding.layoutSearchHistory.isVisible = true

        }
        adapterHistorySearch.submitList(modelList)

    }


    private fun getSearchHistory() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.getSearch()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun addListenerOnView() {
        binding.edLocation.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            binding.edLocation.hint = ""
        }

        binding.edLocation.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                binding.edLocation.hint = getString(R.string.search_location)
                if(searchList.isNotEmpty())
                binding.layoutSearchHistory.isVisible = true
            }
        }

        binding.imgSearch.setOnClickListener {
            if (validateForm()) {
                val bundle = Bundle().apply {
                    putString(Nav.Weather.location, binding.edLocation.text.toString())
                }
                findNavController().navigate(R.id.weatherFragment, bundle)
            }
        }
    }

    private fun initViews() {
        binding.rvSearchHistory.adapter = adapterHistorySearch
    }

    private fun validateForm(): Boolean {
        return !binding.edLocation.isEmpty(getString(R.string.search_for_a_city))
    }

}