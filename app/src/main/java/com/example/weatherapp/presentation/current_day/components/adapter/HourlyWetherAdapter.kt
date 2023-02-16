package com.example.weatherapp.presentation.current_day.components.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.common.getFormattedHour
import com.example.weatherapp.common.getFormattedTime
import com.example.weatherapp.databinding.HourlyItemBinding
import com.example.weatherapp.domain.model.DailyWeather
import com.example.weatherapp.domain.model.HourlyWeather

class HourlyWetherAdapter (private val hourlyWeather: HourlyWeather,private val dailyWeather: DailyWeather, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.hourly_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder){
            val binding=HourlyItemBinding.bind(holder.itemView)


            binding.hourTv.text= getFormattedHour(getFormattedTime(hourlyWeather.time.get(position).toString()))
            binding.temprtureTv.text =hourlyWeather.temperature_2m.get(position).toInt().toString()+"°"
            bindWeatherStatusImageHourly(binding.firstLayerIconIv,binding.secLayerIconIv,hourlyWeather.weathercode.get(position),dailyWeather.sunrise.get(0),
            dailyWeather.sunset.get(0),hourlyWeather.time.get(position))
        }
    }

    override fun getItemCount(): Int {
        return hourlyWeather.rain.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}