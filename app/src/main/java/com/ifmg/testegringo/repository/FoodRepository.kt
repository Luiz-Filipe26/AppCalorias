package com.ifmg.testegringo.repository

import android.content.ContentValues
import android.content.Context
import com.ifmg.testegringo.localdata.DatabaseContract
import com.ifmg.testegringo.localdata.DatabaseSQLite
import com.ifmg.testegringo.model.Food
import java.util.Date

class FoodRepository(context: Context) {

    lateinit var database:DatabaseSQLite

    init {
        database = DatabaseSQLite(context)
    }

    fun getAllFoods(): List<Food> {
        val dataBaseRead = database.readableDatabase
        val foodList = mutableListOf<Food>()

        val cursor = dataBaseRead.query(
            DatabaseContract.FOOD.TABLE_NAME,
            null, // Seleciona todas as colunas
            null, // Sem cláusula WHERE
            null, // Sem valores para o WHERE
            null, // Sem agrupamento
            null, // Sem filtros de grupo
            null  // Sem ordem específica
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.FOOD.COLUMN_NAME_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FOOD.COLUMN_NAME_NAME))
                val calories = getFloat(getColumnIndexOrThrow(DatabaseContract.FOOD.COLUMN_NAME_CALORIES))
                val day = getLong(getColumnIndexOrThrow(DatabaseContract.FOOD.COLUMN_NAME_DAY))

                val food = Food(id, name, calories, Date(day))
                foodList.add(food)
            }
            close()
        }

        return foodList
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

    fun removeFood(food: Food) {
        val dataBaseEdit = database.writableDatabase
        val whereClause = "${DatabaseContract.FOOD.COLUMN_NAME_ID} = ?"
        val whereArgs = arrayOf(food.id.toString())

        dataBaseEdit.delete(DatabaseContract.FOOD.TABLE_NAME, whereClause, whereArgs)
    }


}