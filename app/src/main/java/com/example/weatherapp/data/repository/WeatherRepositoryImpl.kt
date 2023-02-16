package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.repository.WeatherRepository
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi)
    : WeatherRepository{
    override suspend fun getWeather(
        latitude : Float,
        longitude : Float,
        hourly : String,
        daily : String,
        timeFormat: String,
        timezone : String,
        start_date : String,
        end_date : String
    ): WeatherDto {
        return api.getWeather(latitude, longitude, hourly, daily, timeFormat, timezone, start_date, end_date)
    }
}