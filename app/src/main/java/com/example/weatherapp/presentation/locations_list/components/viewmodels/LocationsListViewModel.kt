package com.example.weatherapp.presentation.locations_list.components.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.domain.use_case.DeleteTimeZoneUseCase
import com.example.weatherapp.domain.use_case.FetchTimeZoneUseCase
import com.example.weatherapp.domain.use_case.UpdateTimeZoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsListViewModel @Inject constructor(private val fetchTimeZoneUseCase: FetchTimeZoneUseCase,
                                                 private val updateTimeZoneUseCase: UpdateTimeZoneUseCase,
                                                 private val deleteTimeZoneUseCase: DeleteTimeZoneUseCase
) : ViewModel(){





    private val _getAllTimeZonesResponse = MutableStateFlow<Resource<List<TimeZone>>>(Resource.Loading())
    val getAllTimeZonesResponse get() = _getAllTimeZonesResponse


    private val _updateTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val updateTimeZoneResponse get() = _updateTimeZoneResponse


    private val _deleteTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val deleteTimeZoneResponse get() = _deleteTimeZoneResponse


    private val _deleteAllTimeZonesResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val deleteAllTimeZonesResponse get() = _deleteAllTimeZonesResponse


    private val _favTimeZoneResponse = MutableStateFlow<Resource<TimeZone>>(Resource.Loading())
    val favTimeZoneResponse get() = _favTimeZoneResponse

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

    fun updateTimeZone(timeZone: TimeZone)=viewModelScope.launch {
        updateTimeZoneUseCase.invoke(timeZone).collect{
            when(it)
            {
                is Resource.Error -> _updateTimeZoneResponse.value =
                    Resource.Error(it.message!!)
                is Resource.Loading -> _updateTimeZoneResponse.value = Resource.Loading()
                is Resource.Success -> {
                    _updateTimeZoneResponse.value = Resource.Success(it.data!!)
                }
            }
        }
    }

    fun deleteTimeZone(timeZone: TimeZone)=viewModelScope.launch {
        deleteTimeZoneUseCase.deleteTimeZone(timeZone).collect{
            when(it)
            {
                is Resource.Error -> _deleteTimeZoneResponse.value =
                    Resource.Error(it.message!!)
                is Resource.Loading -> _deleteTimeZoneResponse.value = Resource.Loading()
                is Resource.Success -> {
                    _deleteTimeZoneResponse.value = Resource.Success(it.data!!)
                }
            }
        }
    }

    fun deleteAllTimeZones()=viewModelScope.launch {
        deleteTimeZoneUseCase.deleteAllTimeZones().collect{
            when(it)
            {
                is Resource.Error -> _deleteAllTimeZonesResponse.value =
                    Resource.Error(it.message!!)
                is Resource.Loading -> _deleteAllTimeZonesResponse.value = Resource.Loading()
                is Resource.Success -> {
                    _deleteAllTimeZonesResponse.value = Resource.Success(it.data!!)
                }
            }
        }
    }



}