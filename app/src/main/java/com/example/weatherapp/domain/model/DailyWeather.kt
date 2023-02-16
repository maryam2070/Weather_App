package com.example.weatherapp.domain.model

data class DailyWeather(
    val weathercode:List<Long>,
    val precipitation: List<Double>,
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val maxTemperatures: List<Double>,
    val minTemperatures: List<Double>,
    val maxWindSpeed: List<Double>,
    val rainSum: List<Double>,
    val showerSum: List<Double>,
    val snowSum: List<Double>
) : java.io.Serializable
