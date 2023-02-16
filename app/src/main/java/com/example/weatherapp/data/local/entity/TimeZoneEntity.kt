package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import com.example.weatherapp.domain.model.TimeZone

@Entity
data class TimeZoneEntity(
    val latitude: String,
    val longitude: String,
    @PrimaryKey val timezone_id: String,
    val fav:Boolean
):java.io.Serializable

fun TimeZoneEntity.toTimeZone():TimeZone
{
    return TimeZone(latitude,longitude,timezone_id,fav)
}