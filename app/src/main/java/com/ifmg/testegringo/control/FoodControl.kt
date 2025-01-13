package com.ifmg.testegringo.control

import android.content.Context
import com.ifmg.testegringo.localdata.LocalCache
import com.ifmg.testegringo.model.Food
import com.ifmg.testegringo.repository.FoodRepository
import java.io.Serializable
import java.util.Calendar
import java.util.Date

//o contexto é passado pois o acesso à Cach. deve ser por APP espec.
class FoodControl(var context:Context) {

    lateinit var localCache: LocalCache
    lateinit var foodRepository: FoodRepository

    init {
        localCache = LocalCache(context)
        foodRepository = FoodRepository(context)
    }

    fun addTotalCalories(calories:Float){
        if(calories > 0){
            localCache.addTotalCalories(calories)
        }
    }

    fun removeTotalCalories(calories:Float){
        if(calories > 0){
            localCache.removeTotalCalories(calories)
        }
    }

    fun getTotalCaloreies():Float{
        return localCache.getTotalCalories()
    }

    fun getLastDay():Date{
        val timeLong = localCache.getLastDayFood()

        val date:Date = Date(timeLong)
        return date
    }

    fun setLastDay(date:Date):Boolean{
        val lastDate:Date = getLastDay()

        val calOld = Calendar.getInstance().apply { time = lastDate }
        val calNew = Calendar.getInstance().apply { time = date }

        if( calOld.get(Calendar.YEAR) != calNew.get(Calendar.YEAR) ||
                calOld.get(Calendar.MONTH) != calNew.get(Calendar.MONTH) ||
                calOld.get(Calendar.DAY_OF_MONTH) != calNew.get(Calendar.DAY_OF_MONTH)){
            localCache.newDay()
            localCache.setLastDayFood(date.time)
            return true
        }else{
            return false
        }

    }

    fun loadAllFoods(): List<Food> {
        return foodRepository.getAllFoods()
    }

    fun removeFood(food: Food) {
        foodRepository.removeFood(food)
    }


    fun registerFoodDatabase(food:Food):Long{

        val today = Calendar.getInstance()

        val foodDay = Calendar.getInstance()
        foodDay.time = food.date

        if(foodDay.before(today)){
            return foodRepository.registerFood(food)
        }

        return -1L
    }



}