package com.example.weatherapp.weatherActivity.presentation.fragment.searchlocation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.domain.interactor.RoomLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(private val roomLocalUseCase: RoomLocalUseCase) : ViewModel() {
    
    private val _searchState =
        MutableStateFlow<SearchActivityState>(SearchActivityState.Init)
    val searchState: StateFlow<SearchActivityState> get() = _searchState
    
    
    suspend fun getSearch(){
        _searchState.value=SearchActivityState.Success(roomLocalUseCase.invokeSearch())
    }

}

sealed class SearchActivityState {
    object Init : SearchActivityState()
    data class Success(val modelSearchHistory: List<ModelSearchHistory>) :
        SearchActivityState()
}
