package com.example.weatherapp.weatherActivity.presentation.di
import com.example.weatherapp.setuproom.RoomDB
import com.example.weatherapp.setuproom.RoomDao
import com.example.weatherapp.weatherActivity.data.repositoryimp.RoomRepositoryLocalImp
import com.example.weatherapp.weatherActivity.domain.repository.RoomRepositoryLocal
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideDao(roomDB: RoomDB): RoomDao = roomDB.roomDao()!!


    @Provides
    @Singleton
    fun provideRoomRepositoryLocal(roomDao: RoomDao): RoomRepositoryLocal =
        RoomRepositoryLocalImp(roomDao)


}