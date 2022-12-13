package com.example.weatherapp.setuproom
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.weatherActivity.data.responseremote.history.ModelSearchHistory

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSearchItem(searchItem: List<ModelSearchHistory>)

    @Query("select * from search")
    fun getSearchHistory(): List<ModelSearchHistory>?

}