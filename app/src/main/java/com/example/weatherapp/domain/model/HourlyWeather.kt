package com.example.weatherapp.domain.model

data class HourlyWeather(
    val temperature_2m: List<Double>,
    val time: List<Long>,
    val rain : List<Double>,
    val weathercode : List<Int>
) :java.io.Serializable
