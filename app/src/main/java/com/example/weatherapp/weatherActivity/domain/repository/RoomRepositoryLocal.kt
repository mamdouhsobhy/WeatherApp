package com.example.weatherapp.weatherActivity.domain.repository

import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory


interface RoomRepositoryLocal {

    suspend fun insertSearch(search: List<ModelSearchHistory>)

    suspend fun getSearchList(): List<ModelSearchHistory>

}