package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.HourlyWeather

data class HourlyDto(
    val temperature_2m: List<Double>,
    val time: List<Long>,
    val rain : List<Double>,
    val weathercode : List<Int>
)

fun HourlyDto.toHourly(): HourlyWeather {
    return HourlyWeather(
        temperature_2m, time,rain,weathercode
    )
}