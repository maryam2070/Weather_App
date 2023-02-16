package com.example.weatherapp.common

object Constants {

    const val hourly = "temperature_2m,rain,weathercode"
    const val daily =
        "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,showers_sum,snowfall_sum,windspeed_10m_max"

    const val BASE_URL = "https://api.open-meteo.com/"

    const val TIME_ZONE_BASE_URL = "https://api.wheretheiss.at/"
}

fun getWeatherStatusByCode(code:Int): String {
    when (code) {
        0 ->  return "Clear sky" //
        1 ->  return "Mainly clear"//
        2 ->  return "partly cloudy"//
        3 ->  return "overcast"
        45 -> return "Fog"
        48 -> return "depositing rime fog"
        51 -> return "Light Drizzle"
        53 -> return "moderate Drizzle"
        55 -> return "dense intensity Drizzle"
        56->return "Light Freezing Drizzle"
        57->return "dense intensity Freezing Drizzle"
        61->return "Slight intensity"
        63->return "moderate intensity"
        65->return "heavy intensity"
        66->return "Light Freezing Rain"
        67->return "heavy Freezing Rain"
        71->return "Slight Snow fall"//
        73->return "moderate Snow fall"//
        75->return "heavy Snow fall"//
        77->return "Snow grains"
        80->return "Slight Rain showers"///
        81->return "moderate Rain showers"//
        82->return "violent Rain showers"//
        85->return "slight Snow showers"//
        86->return "heavy Snow showers"//
        95->return "moderate Thunderstorm"
        96->return "slight Thunderstorm"
        99->return "heavy Thunderstorm"
        else->
            return ""
    }
}
