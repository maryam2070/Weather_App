package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.DailyWeather

data class DailyDto(
    val weathercode:List<Long>,
    val precipitation_sum: List<Double>,
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val windspeed_10m_max: List<Double>,
    val snowfall_sum: List<Double>,
    val rain_sum: List<Double>,
    val showers_sum: List<Double>
)



fun DailyDto.toDaily(): DailyWeather {
    return DailyWeather(weathercode,precipitation_sum,sunrise,sunset,temperature_2m_max,temperature_2m_min,windspeed_10m_max
    ,rain_sum,showers_sum,snowfall_sum)
}