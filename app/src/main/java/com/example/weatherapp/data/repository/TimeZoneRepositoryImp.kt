package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.TimeZoneDao
import com.example.weatherapp.data.local.entity.TimeZoneEntity
import com.example.weatherapp.data.remote.TimeZoneApi
import com.example.weatherapp.data.remote.dto.TimeZoneDto
import com.example.weatherapp.data.remote.dto.toTimeZoneEnity
import com.example.weatherapp.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeZoneRepositoryImp @Inject constructor(private val api: TimeZoneApi,private val dao: TimeZoneDao)
    : TimeZoneRepository {

    lateinit var timeZoneDto: TimeZoneDto
    override suspend fun getTimeZone(
        latlong  : String
    ): TimeZoneDto {
        timeZoneDto=api.getTimeZone(latlong)
        insertTimeZone(timeZoneDto.toTimeZoneEnity())
        return timeZoneDto
    }

    override suspend fun insertTimeZone(timeZoneEntity: TimeZoneEntity) {
        dao.insetTimeZone(timeZoneEntity)
    }

    override suspend fun deleteTimeZone(timeZoneId:String) {
        dao.deleteTimeZone(timeZoneId)
    }

    override suspend fun deleteAllTimeZones() {
        dao.deleteAllTimeZones()
    }

    override suspend fun updateTimeZone(timeZoneEntity: TimeZoneEntity) {
        dao.updateTimeZone(timeZoneEntity)
    }

    override fun getAllTimeZones(): Flow<List<TimeZoneEntity>> {
        return dao.getAll()
    }

    override suspend  fun getFavTimeZone(): TimeZoneEntity {
        return dao.getFavTimeZone()
    }
}