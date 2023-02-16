package com.example.weatherapp.domain.use_case

import android.util.Log
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.entity.toTimeZone
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.toTimeZoneEntity
import com.example.weatherapp.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateTimeZoneUseCase @Inject constructor(private val repository: TimeZoneRepository) {

    fun invoke(timeZone: TimeZone): Flow<Resource<TimeZone>> = flow {
        emit(Resource.Loading())
        try {
            repository.updateTimeZone(timeZone.toTimeZoneEntity())
            emit(Resource.Success(timeZone))

        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
            Log.d("updateTimeZoneUseCase", e.message.toString())
        }

    }
}