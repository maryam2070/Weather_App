package com.example.weatherapp.domain.use_case

import android.util.Log
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.remote.dto.toTimeZone
import com.example.weatherapp.data.remote.dto.toTimeZoneEnity
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.toTimeZoneEntity
import com.example.weatherapp.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InsertTimeZoneUseCase @Inject constructor(private val repository: TimeZoneRepository) {

    fun invoke(timeZone: TimeZone): Flow<Resource<TimeZone>> = flow {
            emit(Resource.Loading())
            try {
                repository.insertTimeZone(timeZone.toTimeZoneEntity())
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
                Log.d("insertTimeZoneUseCase", e.message.toString())
            }
            emit(Resource.Success(timeZone))

    }
}