package com.example.weatherapp.domain.model

data class Weather (
    val daily: DailyWeather,
    val hourly: HourlyWeather,
    val latitude: Float,
    val longitude: Float,
    val timezone: String
) : java.io.Serializable