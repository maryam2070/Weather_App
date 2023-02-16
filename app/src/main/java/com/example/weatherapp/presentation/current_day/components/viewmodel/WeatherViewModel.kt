package com.example.weatherapp.presentation.current_day.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.FetchTimeZoneUseCase
import com.example.weatherapp.domain.use_case.GetCurrentDayUseCase
import com.example.weatherapp.domain.use_case.UpdateTimeZoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getCurrentDayUseCase: GetCurrentDayUseCase,
                                           private val fetchTimeZoneUseCase: FetchTimeZoneUseCase,
                                           private val updateTimeZoneUseCase: UpdateTimeZoneUseCase) :ViewModel(){


    private val _getCurrentWeatherResponse = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val getCurrentWeatherResponse get() = _getCurrentWeatherResponse

    private val _getWeeklyWeatherResponse = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val getWeeklyWeatherResponse get() = _getWeeklyWeatherResponse


    private val _getAllTimeZonesResponse = MutableStateFlow<Resource<List<TimeZone>>>(Resource.Loading())
    val getAllTimeZonesResponse get() = _getAllTimeZonesResponse



    private val _favTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val favTimeZoneResponse get() = _favTimeZoneResponse

    fun getCurrentDayWeather(
        latitude: Float,
        longitude: Float,
        timezone: String,
        start_date: String,
        end_date: String
    ) = viewModelScope.launch {
        getCurrentDayUseCase.invoke(latitude, longitude, timezone, start_date, end_date)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _getCurrentWeatherResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _getCurrentWeatherResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        _getCurrentWeatherResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }

    fun getAllTimeZones()=viewModelScope.launch {
        fetchTimeZoneUseCase.getAllTimeZones().collect{
            when(it)
            {
                is Resource.Error -> _getAllTimeZonesResponse.value =
                    Resource.Error(it.message!!)
                is Resource.Loading -> _getAllTimeZonesResponse.value = Resource.Loading()
                is Resource.Success -> {
                    _getAllTimeZonesResponse.value = Resource.Success(it.data!!)
                }
            }
        }
    }

    fun getFavTimeZone()=viewModelScope.launch {
        fetchTimeZoneUseCase.getFavTimeZones().collect{
            when(it)
            {
                is Resource.Error -> _favTimeZoneResponse.value =
                    Resource.Error(it.message!!)
                is Resource.Loading -> _favTimeZoneResponse.value = Resource.Loading()
                is Resource.Success -> {
                    _favTimeZoneResponse.value = Resource.Success(it.data!!)
                }
            }
        }
    }

    fun getWeeklyWeather(
        latitude: Float,
        longitude: Float,
        timezone: String,
        start_date: String,
        end_date: String
    ) = viewModelScope.launch {
        getCurrentDayUseCase.invoke(latitude, longitude, timezone, start_date, end_date)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _getWeeklyWeatherResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _getWeeklyWeatherResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        _getWeeklyWeatherResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }


}