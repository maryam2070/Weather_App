package com.example.weatherapp.presentation.current_day.components.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.common.Resource
import com.example.weatherapp.databinding.LocationItemBinding
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.presentation.current_day.components.viewmodel.WeatherViewModel
import com.example.weatherapp.presentation.locations_list.components.ui.LocationsListFragment
import com.example.weatherapp.presentation.locations_list.components.viewmodels.LocationsListViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@DelicateCoroutinesApi
class TimeZoneAdapter @Inject constructor(val list: ArrayList<TimeZone>, val context: Context, val viewModel: LocationsListViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.location_item,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder){

            val binding= LocationItemBinding.bind(holder.itemView)
            val model=list.get(position)
            binding.timezoneTv.text=model.timezone_id.substring(model.timezone_id.indexOf('/')+1)

            if(model.fav)
                binding.favIc.setImageResource(R.drawable.ic_baseline_favorite_24)
            else
                binding.favIc.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            binding.favIc.setOnClickListener {
                var fav: TimeZone? = null
                viewModel.getFavTimeZone()


            CoroutineScope(IO).launch {
                        println("AAAAA ccccccccccccccccccc")
                        viewModel.favTimeZoneResponse.collect {
                            when (it) {
                                is Resource.Loading-> println("AAAAA Loading")
                                is Resource.Error-> println("AAAAA Error")
                                is Resource.Success-> {

                                    fav = it.data
                                    fav!!.fav = false
                                    viewModel.updateTimeZone(fav!!)

                                    model.fav = true
                                    viewModel.updateTimeZone(model)

                                }
                            }
                        }

                    }

            }
            holder.itemView.setOnClickListener {
                val args= Bundle()
                args.putString("time_zone",model.timezone_id)
                args.putString("latitude",model.latitude)
                args.putString("longitude",model.longitude)
                it.findNavController().navigate(R.id.action_locationsListFragment_to_weatherFragment,args)
            }

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}