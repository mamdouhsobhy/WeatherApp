package com.example.weatherapp.weatherActivity.data.repositoryimp

import com.example.weatherapp.setuproom.RoomDao
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import com.example.weatherapp.weatherActivity.domain.repository.RoomRepositoryLocal
import javax.inject.Inject


/**
 * This repository is responsible for
 * fetching data from  db
 */
class RoomRepositoryLocalImp @Inject constructor(private val roomDao: RoomDao) :
    RoomRepositoryLocal {

    override suspend fun insertSearch(search: List<ModelSearchHistory>) {
        roomDao.insertSearchItem(search)
    }

    override suspend fun getSearchList(): List<ModelSearchHistory> {
        return roomDao.getSearchHistory()!!
    }
}