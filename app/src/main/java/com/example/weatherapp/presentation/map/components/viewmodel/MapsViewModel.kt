package com.example.weatherapp.presentation.map.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetCurrentDayUseCase
import com.example.weatherapp.domain.use_case.GetTimeZoneUseCase
import com.example.weatherapp.domain.use_case.InsertTimeZoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val getTimeZoneUseCase: GetTimeZoneUseCase,
                                        private val insertTimeZoneUseCase: InsertTimeZoneUseCase) :
    ViewModel(){

    private val _getTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val getTimeZoneResponse get() = _getTimeZoneResponse


    private val _insertTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val insertTimeZoneResponse get() = _insertTimeZoneResponse

    fun getTimeZone(latlong : String) = viewModelScope.launch {
        getTimeZoneUseCase.invoke(latlong)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _getTimeZoneResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _getTimeZoneResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        Log.i("Time zone data is: ", response.data.toString())
                        _getTimeZoneResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }

    fun insertTimeZone(timeZone: TimeZone) = viewModelScope.launch {
        insertTimeZoneUseCase.invoke(timeZone)
            .collect { response ->
                when (response) {
                    is Resource.Error -> _insertTimeZoneResponse.value =
                        Resource.Error(response.message!!)
                    is Resource.Loading -> _insertTimeZoneResponse.value = Resource.Loading()
                    is Resource.Success -> {
                        Log.i("Time zone data is: ", response.data.toString())
                        _insertTimeZoneResponse.value =
                            Resource.Success(response.data!!)
                    }
                }
            }
    }

}