package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Weather

data class WeatherDto(
    val daily: DailyDto,
    val hourly: HourlyDto,
    val latitude: Float,
    val longitude: Float,
    val timezone: String
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        daily.toDaily(),
        hourly.toHourly(),
        latitude, longitude, timezone
    )
}