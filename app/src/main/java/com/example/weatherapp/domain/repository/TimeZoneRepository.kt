package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.local.entity.TimeZoneEntity
import com.example.weatherapp.data.remote.dto.TimeZoneDto
import kotlinx.coroutines.flow.Flow

interface TimeZoneRepository {

    suspend fun getTimeZone(
        latlong : String):TimeZoneDto
    suspend fun insertTimeZone(timeZoneEntity: TimeZoneEntity)
    suspend fun deleteTimeZone(timeZoneId:String)
    suspend fun deleteAllTimeZones()
    suspend fun updateTimeZone(timeZoneEntity: TimeZoneEntity)
    fun getAllTimeZones():Flow<List<TimeZoneEntity>>
    fun getFavTimeZone():Flow<TimeZoneEntity>
}