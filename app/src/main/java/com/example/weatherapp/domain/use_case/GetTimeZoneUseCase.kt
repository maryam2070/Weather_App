package com.example.weatherapp.domain.use_case

import android.util.Log
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.TimeZoneDao
import com.example.weatherapp.data.remote.dto.TimeZoneDto
import com.example.weatherapp.data.remote.dto.toTimeZone
import com.example.weatherapp.data.remote.dto.toTimeZoneEnity
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.TimeZoneRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTimeZoneUseCase @Inject constructor(private val repository: TimeZoneRepository) {

    fun invoke(latlong : String): Flow<Resource<TimeZone>> = flow {
        try{
            emit(Resource.Loading())
            try {
                val timeZone = repository.getTimeZone(latlong)
                emit(Resource.Success(timeZone!!.toTimeZone()))
            }
            catch (e: IOException)
            {
                emit(Resource.Error(e.message.toString()))
                Log.d("GetTimeZoneUseCase",e.message.toString())
            }

        }
        catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        } catch (ex: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}