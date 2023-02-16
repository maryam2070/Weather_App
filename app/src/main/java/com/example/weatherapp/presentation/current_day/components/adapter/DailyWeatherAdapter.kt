package com.example.weatherapp.presentation.current_day.components.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.common.addDaysPeriodToCalendar
import com.example.weatherapp.common.getCurrentDay
import com.example.weatherapp.common.getDayName
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.domain.model.DailyWeather


class DailyWeatherAdapter (private val dailyWeather: DailyWeather, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.daily_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder){
            val binding= DailyItemBinding.bind(holder.itemView)
            bindWeatherStatusImageDaily(binding.firstLayerIconIv,binding.secLayerIconIv,dailyWeather.weathercode.get(position).toInt())
            binding.dayTv.text= getDayName(addDaysPeriodToCalendar(getCurrentDay(),position))
            binding.hiLoTmpTv.text=dailyWeather.maxTemperatures.get(position).toInt().toString()+"°/"+dailyWeather.minTemperatures.get(position).toInt().toString()+"°"
        }
    }

    override fun getItemCount(): Int {
        return dailyWeather.maxTemperatures.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}