package com.example.weatherapp.domain.use_case

import android.util.Log
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.toTimeZoneEntity
import com.example.weatherapp.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteTimeZoneUseCase @Inject constructor(private val repository: TimeZoneRepository) {

    fun deleteTimeZone(timeZone: TimeZone): Flow<Resource<TimeZone>> = flow {
        emit(Resource.Loading())
        try {
            repository.deleteTimeZone(timeZone.timezone_id)
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
            Log.d("deleteTimeZoneUseCase", e.message.toString())
        }
        emit(Resource.Success(timeZone))
    }


    fun deleteAllTimeZones(): Flow<Resource<TimeZone>> = flow {
        emit(Resource.Loading())
        try {
            repository.deleteAllTimeZones()
            //emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
            Log.d("deleteTimeZoneUseCase", e.message.toString())
        }
    }
}