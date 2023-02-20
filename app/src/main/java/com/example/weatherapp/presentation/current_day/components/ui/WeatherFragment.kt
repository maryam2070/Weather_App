package com.example.weatherapp.presentation.current_day.components.ui


import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.withCreated
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.common.*
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.current_day.components.adapter.DailyWeatherAdapter
import com.example.weatherapp.presentation.current_day.components.adapter.HourlyWetherAdapter
import com.example.weatherapp.presentation.current_day.components.adapter.bindWeatherStatusImageHourlyWithAnimation
import com.example.weatherapp.presentation.current_day.components.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class ModalBottomSheet : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_dialog, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}

@AndroidEntryPoint
class WeatherFragment : Fragment() {


    private lateinit var viewModel: WeatherViewModel
    lateinit var binding: FragmentWeatherBinding
    lateinit var editor:SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)


        sharedPreferences=requireContext().getSharedPreferences("shared_file", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()


        binding.searchBar.setOnClickListener {
            findNavController().navigate(com.example.weatherapp.R.id.action_weatherFragment_to_mapsFragment)

        }
        binding.loadingPb.visibility = View.INVISIBLE
        binding.container.visibility=View.INVISIBLE

          viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.getAllTimeZones()
        binding.historyIv.setOnClickListener {

            findNavController().navigate(com.example.weatherapp.R.id.action_weatherFragment_to_locationsListFragment)
        }
        if (arguments != null) {
            viewModel.getCurrentDayWeather(
                requireArguments().get("latitude").toString().toFloat(),
                requireArguments().get("longitude").toString().toFloat(),
                requireArguments().get("time_zone").toString(),
                getFormattedDate(getCurrentDay()),
                getFormattedDate(getCurrentDay())
            )
            viewModel.getWeeklyWeather(
                requireArguments().get("latitude").toString().toFloat(),
                requireArguments().get("longitude").toString().toFloat(),
                requireArguments().get("time_zone").toString(),
                getFormattedDate(getCurrentDay()),
                getFormattedDate(addDaysPeriodToCalendar(getCurrentDay(), 6))
            )
        }else{
            viewModel.getFavTimeZone()
            CoroutineScope(Dispatchers.Main).launch {

                viewModel.favTimeZoneResponse.collect { response ->

                        when (response) {
                            is Resource.Error -> {
                                binding.loadingPb.visibility = View.INVISIBLE
                                Toast.makeText(
                                    requireContext(),
                                    response.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            is Resource.Loading -> {
                                binding.loadingPb.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.loadingPb.visibility = View.INVISIBLE
                                val data = response.data!!
                                if (data.latitude.isNotBlank()) {
                                    editor.putBoolean("have_fav", true).apply()
                                    viewModel.getCurrentDayWeather(
                                        data.latitude.toFloat(),
                                        data.longitude.toFloat(),
                                        data.timezone_id,
                                        getFormattedDate(getCurrentDay()),
                                        getFormattedDate(getCurrentDay())
                                    )
                                    viewModel.getWeeklyWeather(
                                        data.latitude.toFloat(),
                                        data.longitude.toFloat(),
                                        data.timezone_id,
                                        getFormattedDate(getCurrentDay()),
                                        getFormattedDate(
                                            addDaysPeriodToCalendar(
                                                getCurrentDay(),
                                                6
                                            )
                                        )
                                    )
                                } else {
                                    editor.putBoolean("have_fav", false).apply()
                                    binding.container.visibility = View.INVISIBLE
                                    showChooseLocationDialog()
                                }
                            }
                        }

                    }

                }
            }



        CoroutineScope(Dispatchers.Main).launch{
            viewModel.getCurrentWeatherResponse.collect { response ->
                when (response) {
                    is Resource.Error -> {
                        binding.loadingPb.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Loading -> {
                        binding.loadingPb.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingPb.visibility = View.INVISIBLE
                        binding.container.visibility=View.VISIBLE
                        updateUI(response.data!!)

                    }
                }

            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeeklyWeatherResponse.collect { response ->
                when (response) {
                    is Resource.Error -> {
                        binding.loadingPb.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Loading -> {
                        binding.loadingPb.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingPb.visibility = View.INVISIBLE
                        updateWeeklyUI(response.data!!)
                        this.cancel()
                    }

                }

            }
        }

        return binding.root
    }

    private fun showChooseLocationDialog() {
        AlertDialog.Builder(requireContext()).setMessage(
            "you need to select Location to start using App"
        )
            .setPositiveButton("go")
            { dialog, _ ->
                try {
                    dialog.dismiss()
                    findNavController().navigate(com.example.weatherapp.R.id.action_weatherFragment_to_mapsFragment)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setCancelable(false)
            .setNegativeButton("") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    private fun updateWeeklyUI(data: Weather) {
        val myLinearLayoutManager =
            object : LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false) {}
        binding.dailyRv.adapter = DailyWeatherAdapter(data.daily, requireContext())
        binding.dailyRv.layoutManager = myLinearLayoutManager

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI(data: Weather) {

        binding.dateTv.text = getDayName(getCurrentDay()) + ", " + getFormattedDate(getCurrentDay())
        binding.lowHighTmpTv.text = data.daily.maxTemperatures.get(0).toInt()
            .toString() + "° /" + data.daily.minTemperatures.get(0).toInt().toString() + "°"
        binding.townTv.text = data.timezone.substring(data.timezone.indexOf('/') + 1)
        val hw = data.hourly
        binding.hourlyRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyRv.adapter = HourlyWetherAdapter(hw,data.daily, requireContext())
        binding.weatherTypeTv.text = getWeatherStatusByCode(data.hourly.weathercode[0])

        binding.rainTv.text = data.daily.rainSum.get(0).toString() + " mm"

        binding.sunriseTv.text =
            getFormattedHour(getFormattedTime(data.daily.sunrise.get(0).toString()))
        binding.sunsetTv.text =
            getFormattedHour(getFormattedTime(data.daily.sunset.get(0).toString()))
        binding.windSpeedTv.text = data.daily.maxWindSpeed.get(0).toString() + " km/h"


        binding.snowFallTv.text = data.daily.snowSum.get(0).toString() + " cm"
        binding.showerTv.text = data.daily.showerSum.get(0).toString() + " mm"
        binding.precipitationTv.text = data.daily.precipitation.get(0).toString() + " mm"

        binding.currentTmpTv.text =
            data.hourly.temperature_2m.get(getCurrentHourIndex(getCurrentDay())).toInt()
                .toString() + "°"


        bindWeatherStatusImageHourlyWithAnimation(binding.firstLayerIconIv,binding.secLayerIconIv,data.hourly.weathercode.get(getCurrentHourIndex(getCurrentDay())),
            data.daily.sunset.get(0),data.daily.sunrise.get(0),data.hourly.time.get(getCurrentHourIndex(getCurrentDay())))

    }

}