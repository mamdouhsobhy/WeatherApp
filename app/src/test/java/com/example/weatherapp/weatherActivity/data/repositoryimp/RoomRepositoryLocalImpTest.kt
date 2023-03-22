package com.example.weatherapp.weatherActivity.data.repositoryimp

import com.example.weatherapp.setuproom.RoomDao
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RoomRepositoryLocalImpTest {

    @Mock
    lateinit var roomDao: RoomDao

    lateinit var repository: RoomRepositoryLocalImp

    @Before
    fun setUp() {
        repository = RoomRepositoryLocalImp(roomDao)
    }

    @Test
    fun `test insertSearch`() = runBlocking {
        val searchList = listOf(ModelSearchHistory(1, "giza"))
        repository.insertSearch(searchList)
        verify(roomDao).insertSearchItem(searchList)
    }

    @Test
    fun `test getSearchList`() = runBlocking {
        val expectedSearchList = listOf(ModelSearchHistory(1, "giza"))
        `when`(roomDao.getSearchHistory()).thenReturn(expectedSearchList)
        val actualSearchList = repository.getSearchList()
        assertEquals(expectedSearchList, actualSearchList)
    }

}