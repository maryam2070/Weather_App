package com.example.weatherapp.domain.model

import com.example.weatherapp.data.local.entity.TimeZoneEntity

data class TimeZone(
    val latitude: String,
    val longitude: String,
    val timezone_id: String,
    var fav:Boolean
):java.io.Serializable

fun TimeZone.toTimeZoneEntity():TimeZoneEntity
{
    return TimeZoneEntity(latitude,longitude,timezone_id,fav)
}