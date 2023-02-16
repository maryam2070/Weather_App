package com.example.weatherapp.data.remote.dto
import com.example.weatherapp.domain.model.CurrentWeather


data class CurrentWeatherDto(
    val temperature: Double,
    val time: Int,
    val weathercode: Int,
    val winddirection: Double,
    val windspeed: Double
)

fun CurrentWeatherDto.toCurrentWeather() : CurrentWeather {
    return CurrentWeather(
        temperature,
        time,
        winddirection,
        windspeed
    )
}