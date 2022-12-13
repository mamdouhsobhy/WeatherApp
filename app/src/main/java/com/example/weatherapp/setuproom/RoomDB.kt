package com.example.weatherapp.setuproom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory

@Database(entities = [ModelSearchHistory::class], version =1 ,exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao?
}