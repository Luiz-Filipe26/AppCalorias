package com.ifmg.testegringo.localdata

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class LocalCache(var context:Context) {
    //SharedPreferences

    //DataStore - JetPack
    lateinit var cache:SharedPreferences

    init{
        cache = context.getSharedPreferences("calories", Activity.MODE_PRIVATE)
    }

    fun addTotalCalories(cal:Float){
        var totalCal:Float = getTotalCalories()
        totalCal += cal

        //marcando a cache para ser escrita em breve
        val editor:SharedPreferences.Editor = cache.edit()

        editor.putFloat("calTotal", totalCal)
        editor.apply() //escrita na cache ocorre
    }

    fun getTotalCalories():Float{
        return cache.getFloat("calTotal",0.0f)
    }

    fun setLastDayFood(longTime:Long){
        val editor:SharedPreferences.Editor = cache.edit()

        editor.putLong("lastDay", longTime)
        editor.apply()

    }

    fun getLastDayFood():Long{
        return cache.getLong("lastDay",0L)
    }

    fun newDay(){
        val editor:SharedPreferences.Editor = cache.edit()

        editor.putFloat("calTotal", 0.0f)
        editor.apply() //escrita na cache ocorre
    }


}