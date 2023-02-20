package com.example.weatherapp.data.local

import androidx.annotation.Nullable
import androidx.room.*
import com.example.weatherapp.data.local.entity.TimeZoneEntity
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId
import java.util.*

@Dao
interface TimeZoneDao {

    @Query("select * from timezoneentity")
    fun getAll(): Flow<List<TimeZoneEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetTimeZone(timeZoneEntity: TimeZoneEntity)

    @Query("delete from timezoneentity")
    suspend fun deleteAll()


    @Update
    suspend fun updateTimeZone(timeZoneEntity: TimeZoneEntity)

    @Query("delete from timezoneentity where timezone_id = :timeZoneId")
    suspend fun deleteTimeZone(timeZoneId: String)

    @Nullable
    @Query("SELECT * FROM timezoneentity WHERE fav = true")
    suspend fun getFavTimeZone():TimeZoneEntity


    @Query("delete from timezoneentity ")
    suspend fun deleteAllTimeZones()

}