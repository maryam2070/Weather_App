package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.TimeZoneDto
import com.example.weatherapp.data.remote.dto.WeatherDto
import retrofit2.http.*

interface TimeZoneApi {

    @GET("/v1/coordinates/{latlong}")
    suspend fun getTimeZone(
        @Path("latlong") latlong: String) : TimeZoneDto
}