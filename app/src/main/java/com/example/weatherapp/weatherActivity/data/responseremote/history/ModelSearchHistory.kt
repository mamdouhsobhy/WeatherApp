package com.example.weatherapp.weatherActivity.data.responseremote.history

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "search")
data class ModelSearchHistory(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    @ColumnInfo(name = "searchName")
    var searchName:String="",
    @ColumnInfo(name = "selected")
    var selected:Boolean=false
): Parcelable
