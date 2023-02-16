package com.example.weatherapp.domain.use_case

import android.util.Log
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.entity.TimeZoneEntity
import com.example.weatherapp.data.local.entity.toTimeZone
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class FetchTimeZoneUseCase @Inject constructor(private val repository: TimeZoneRepository) {

    fun getAllTimeZones(): Flow<Resource<List<TimeZone>>> = flow {
        emit(Resource.Loading())
        try {
            repository.getAllTimeZones().collect {
                emit(Resource.Success(it.map {
                    it.toTimeZone() }))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
            Log.d("getAllTimeZoneUseCase", e.message.toString())
        }
    }

    fun getFavTimeZones(): Flow<Resource<TimeZone>> = flow {
        emit(Resource.Loading())
        try {
            repository.getFavTimeZone().collect{
                if (it != null)
                    emit(Resource.Success(it.toTimeZone()))
                else
                    emit(Resource.Success(TimeZone("", "", "", false)))
            }


        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
            Log.d("getAllTimeZoneUseCase", e.message.toString())
        }
    }
}