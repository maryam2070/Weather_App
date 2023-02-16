package com.example.weatherapp.data.remote
import com.example.weatherapp.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("hourly", encoded = true) hourly : String,
        @Query("daily", encoded = true) daily : String,
        @Query("timeformat") timeFormat: String,
        @Query("timezone") timezone : String,
        @Query("start_date") start_date : String,
        @Query("end_date") end_date : String
    ) : WeatherDto


}