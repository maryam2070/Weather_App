package com.example.weatherapp.domain.model

data class CurrentWeather(
    val temperature: Double,
    val time: Int,
    val wind_direction: Double,
    val wind_speed: Double
) :java.io.Serializable
