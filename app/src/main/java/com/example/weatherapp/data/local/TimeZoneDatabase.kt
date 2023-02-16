package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.local.entity.TimeZoneEntity


@Database(entities = [TimeZoneEntity::class],version=1)
abstract class TimeZoneDatabase: RoomDatabase(){
    abstract val dao:TimeZoneDao
}