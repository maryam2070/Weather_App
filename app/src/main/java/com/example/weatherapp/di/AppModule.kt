package com.example.weatherapp.di
import android.app.Application
import androidx.room.Room
import com.example.weatherapp.common.Constants.BASE_URL
import com.example.weatherapp.common.Constants.TIME_ZONE_BASE_URL
import com.example.weatherapp.data.local.TimeZoneDao
import com.example.weatherapp.data.local.TimeZoneDatabase
import com.example.weatherapp.data.remote.TimeZoneApi
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.TimeZoneDto
import com.example.weatherapp.data.repository.TimeZoneRepositoryImp
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.TimeZoneRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
    @Provides
    @Singleton
    fun provideWeatherReository(api:WeatherApi):WeatherRepository{
        return WeatherRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideTimeZoneApi(): TimeZoneApi {
        return Retrofit.Builder()
            .baseUrl(TIME_ZONE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimeZoneApi::class.java)
    }
    @Provides
    @Singleton
    fun provideTimeZoneRepository(api:TimeZoneApi,db:TimeZoneDatabase):TimeZoneRepository{
        return TimeZoneRepositoryImp(api,db.dao)
    }

    @Provides
    @Singleton
    fun provideTimeZoneDatabase(app:Application):TimeZoneDatabase{
        return Room.databaseBuilder(app,TimeZoneDatabase::class.java,"timezone_database").build()
    }
}
