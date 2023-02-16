package com.example.weatherapp.presentation.current_day.components.adapter

import android.view.View.INVISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.example.weatherapp.common.*
import java.util.*

@BindingAdapter("statusIcon")
fun bindWeatherStatusImageHourlyWithAnimation(
    stLayer: ImageView,
    secLayer: ImageView,
    weatherCode: Int,
    sunrise: Long,
    sunset: Long,
    time:Long
) {
    when(weatherCode)
    {

        0 -> {
            if (time >= sunrise && time < sunset) {
                stLayer.setImageResource(R.drawable.sun_ic)
                sunAnimation(stLayer)
            } else {
                stLayer.setImageResource(R.drawable.night_ic)
                moonAnimation(stLayer)
            }
        }
        1,2, 3 -> {
            if(time>=sunrise && time<sunset)
                stLayer.setImageResource(R.drawable.sun_ic)
            else
                stLayer.setImageResource(R.drawable.night_ic)
            secLayer.setImageResource(R.drawable.cloud_ic)
            cloudAnimation(secLayer)
        }
        45,48 -> stLayer.setImageResource(R.drawable.foog_ic)

        61, 63, 65,66, 67,80, 81, 82,51, 53, 55,56, 57->{
            stLayer.setImageResource(R.drawable.cloud_ic)
            secLayer.setImageResource(R.drawable.rain_ic)
            rainAnimation(secLayer)
        }

        71, 73, 75,77,85, 86->{
            stLayer.setImageResource(R.drawable.cloud_ic)
            secLayer.setImageResource(R.drawable.snow_drops_ic)
            snowfallAnimation(secLayer)
        }

        95,96,99->
            stLayer.setImageResource(R.drawable.thunder_ic)
    }

}

@BindingAdapter("statusIcon")
fun bindWeatherStatusImageHourly(
    stLayer: ImageView,
    secLayer: ImageView,
    weatherCode: Int,
    sunrise: Long,
    sunset: Long,
    time:Long
) {
    when(weatherCode)
    {

        0 -> {
            if(time>=sunrise && time<sunset) {
                stLayer.setImageResource(R.drawable.sun_ic)
            } else {
                stLayer.setImageResource(R.drawable.night_ic)
            }
            secLayer.visibility=INVISIBLE
        }
        1,2, 3 -> {
            if(time>=sunrise && time<sunset)
                stLayer.setImageResource(R.drawable.sun_ic)
            else
                stLayer.setImageResource(R.drawable.night_ic)
            secLayer.setImageResource(R.drawable.cloud_ic)
        }
        45, 48 -> {
            stLayer.setImageResource(R.drawable.foog_ic)
            secLayer.visibility = INVISIBLE
        }

        61, 63, 65, 66, 67, 80, 81, 82, 51, 53, 55, 56, 57 -> {
            stLayer.setImageResource(R.drawable.rainy_ic)
            secLayer.visibility = INVISIBLE
        }

        71, 73, 75, 77, 85, 86 -> {
            stLayer.setImageResource(R.drawable.snow_flake_ic)
            secLayer.visibility = INVISIBLE
        }
        95, 96, 99 -> {
            stLayer.setImageResource(R.drawable.thunder_ic)
            secLayer.visibility=INVISIBLE
        }
    }

}
fun bindWeatherStatusImageDaily(
    stLayer: ImageView,
    secLayer: ImageView,
    weatherCode: Int
) {
    when(weatherCode) {
        0 -> {
            stLayer.setImageResource(R.drawable.sun_ic)
            secLayer.visibility = INVISIBLE
        }

        1, 2, 3 -> {
            stLayer.setImageResource(R.drawable.sun_ic)
            secLayer.visibility = INVISIBLE
        }

        45, 48 -> {
            stLayer.setImageResource(R.drawable.foog_ic)
            secLayer.visibility = INVISIBLE
        }

        61, 63, 65, 66, 67, 80, 81, 82, 51, 53, 55, 56, 57 -> {
            stLayer.setImageResource(R.drawable.rainy_ic)
            secLayer.visibility = INVISIBLE
        }

        71, 73, 75, 77, 85, 86 -> {
            stLayer.setImageResource(R.drawable.snow_flake_ic)
            secLayer.visibility = INVISIBLE
        }
        95, 96, 99 -> {
            stLayer.setImageResource(R.drawable.thunder_ic)
            secLayer.visibility=INVISIBLE
        }
    }

}







