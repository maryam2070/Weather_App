package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.data.local.entity.TimeZoneEntity
import com.example.weatherapp.domain.model.TimeZone


data class TimeZoneDto(
    val country_code: String,
    val latitude: String,
    val longitude: String,
    val map_url: String,
    val offset: Double,
    val timezone_id: String
)

fun TimeZoneDto.toTimeZone():TimeZone {
    return TimeZone(latitude, longitude, timezone_id,false)
}

fun TimeZoneDto.toTimeZoneEnity():TimeZoneEntity {
    return TimeZoneEntity(latitude, longitude, timezone_id,false)
}