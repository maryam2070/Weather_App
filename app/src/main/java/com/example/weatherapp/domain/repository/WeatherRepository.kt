package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.WeatherDto
import com.google.android.material.timepicker.TimeFormat

interface WeatherRepository {

    suspend fun getWeather(
        latitude : Float,
        longitude : Float,
        hourly: String,
        daily : String,
        timeFormat: String,
        timezone : String,
        start_date : String,
        end_date : String
    ) : WeatherDto
}