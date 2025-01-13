package com.ifmg.testegringo.tools

import android.icu.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

class Formatters{

    companion object{

        fun dayFormatter(day:Date):String{
            val formatter = SimpleDateFormat("dd/MM/yyyy")

            return formatter.format(day)
        }

        fun timeFormatter(day:Date):String{
            val formatter = SimpleDateFormat("HH:mm")

            return formatter.format(day)
        }

    }

}