package com.example.weatherapp.weatherActivity.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.setuproom.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class DataBaseModule {
     //var converter:Converter= Converter()
    @Provides
    @Singleton
    fun provideDB(application: Application): RoomDB = Room.databaseBuilder(application, RoomDB::class.java, "search")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


}