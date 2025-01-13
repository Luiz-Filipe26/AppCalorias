package com.ifmg.testegringo.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.ifmg.testegringo.model.Food

class FoodListAdapter(context: Context) {
    lateinit var itemAdapter:ArrayAdapter<String>
    var foods:MutableList<String> = mutableListOf()

    init {
        itemAdapter = ArrayAdapter(context,
            android.R.layout.simple_list_item_1,
            foods)
    }

    fun addNewFood(food: Food){
        foods.add(food.name)
        itemAdapter.notifyDataSetChanged()
    }

    fun removeFood(pos:Int){
        if(pos < foods.size){
            foods.removeAt(pos)
            itemAdapter.notifyDataSetChanged()
        }
    }

}