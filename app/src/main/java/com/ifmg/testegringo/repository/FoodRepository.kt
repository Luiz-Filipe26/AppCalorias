package com.ifmg.testegringo.repository

import android.content.ContentValues
import android.content.Context
import com.ifmg.testegringo.localdata.DatabaseContract
import com.ifmg.testegringo.localdata.DatabaseSQLite
import com.ifmg.testegringo.model.Food

class FoodRepository(context: Context) {

    lateinit var database:DatabaseSQLite

    init {
        database = DatabaseSQLite(context)
    }

    fun registerFood(food:Food):Long{

        val dataBaseEdit = database.writableDatabase

        val valuesFood:ContentValues = ContentValues()
        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_NAME,food.name)
        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_CALORIES,food.calories)
        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_DAY,food.date.time)

         val idFood = dataBaseEdit.insert(
             DatabaseContract.FOOD.TABLE_NAME,
             null,
             valuesFood)

        return idFood

    }



}