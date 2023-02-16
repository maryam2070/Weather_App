package com.example.weatherapp.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.time.ZoneId.*
import java.util.*
import java.util.TimeZone.getDefault
import java.util.TimeZone.getTimeZone

fun getCurrentDay(): Calendar {
    return Calendar.getInstance()
}

fun getFormattedDate(calendar: Calendar): String {

    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(calendar.time)
}

fun addDaysPeriodToCalendar(calendar: Calendar,days:Int): Calendar {

    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar
}

fun getDayName(calendar: Calendar): String {

    val days= listOf<String>("Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday","Saturday","")
    return days.get(calendar.get(Calendar.DAY_OF_WEEK)-1)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedTime(time:String): String {
    val out=Instant.ofEpochSecond(time.toLong())
        .atZone(systemDefault())
        .toLocalDateTime()
        .toString()
    return out.substring(out.indexOf("T")+1,out.indexOf(":"))
}

fun getFormattedHour(hour:String): String {
    if(hour.equals("00"))
        return "12 AM"
    else if(hour.toInt()<12)
        return hour.toInt().toString()+" AM"
    else if(hour.equals("12"))
        return "12 PM"
    else
        return (hour.toInt()-12).toString()+" PM"
}
fun getCurrentHourIndex(calendar: Calendar):Int{
    val hour= getFormattedHour(calendar.get(Calendar.HOUR_OF_DAY).toString())
    if(hour.equals("12 AM"))
        return 0
    else if(hour.equals("12 PM"))
        return 12
    else if(hour.contains("AM"))
        return hour.subSequence(0,hour.indexOf(" ")).toString().toInt()
    else
        return hour.subSequence(0,hour.indexOf(" ")).toString().toInt()+12
}