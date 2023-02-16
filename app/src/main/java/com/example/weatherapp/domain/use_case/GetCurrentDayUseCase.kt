package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentDayUseCase @Inject constructor(private val repository: WeatherRepository) {

    fun invoke(
        latitude: Float,
        longitude: Float,
        timezone: String,
        start_date: String,
        end_date: String): Flow<Resource<Weather>>  = flow {
            try{
                emit(Resource.Loading())
                val weather = repository.getWeather(
                    longitude,
                    latitude,
                    Constants.hourly,
                    Constants.daily,
                    "unixtime" ,
                    timezone,
                    start_date,
                    end_date).toWeather()
                emit(Resource.Success(weather))
            }
            catch (ex: HttpException) {
                emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred."))
            } catch (ex: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
            }
    }
}