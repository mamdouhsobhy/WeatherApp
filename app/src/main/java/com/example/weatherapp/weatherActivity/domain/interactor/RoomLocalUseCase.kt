package com.example.weatherapp.weatherActivity.domain.interactor
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.domain.repository.RoomRepositoryLocal
import javax.inject.Inject

class RoomLocalUseCase @Inject constructor(private var repository: RoomRepositoryLocal){

    suspend fun invokeSearch(): List<ModelSearchHistory> =repository.getSearchList()

    suspend fun insertSearch(search: List<ModelSearchHistory>) {
        repository.insertSearch(search)
    }

}
