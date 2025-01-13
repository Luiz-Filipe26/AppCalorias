package com.ifmg.testegringo.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ifmg.testegringo.R
import com.ifmg.testegringo.model.Food

class FoodListCustomItem(var context: Context):BaseAdapter() {

    lateinit var itemAdapter: ArrayAdapter<String>
    var foods:MutableList<Food> = mutableListOf()
    lateinit var inflater:LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return foods.size
    }

    override fun getItem(position: Int): Any {
        return foods[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var tempView = convertView

        //será que o item foi RENDER. em algum momento
        if(tempView == null){
            tempView = inflater.inflate(R.layout.food_item_home,parent,false)
            val foodSelected = foods[position]
            tempView.findViewById<TextView>(R.id.name_item).setText(foodSelected.name)
            tempView.findViewById<TextView>(R.id.cal_item).setText(foodSelected.calories.toString())

            registerEvents(tempView)

            return tempView
        }else{
            return tempView
        }

    }

    private fun registerEvents(tempView:View) {
        tempView.findViewById<ImageButton>(R.id.hide_food).setOnClickListener {
            tempView.findViewById<ConstraintLayout>(R.id.container_item_food).setBackgroundColor(Color.RED)
        }
    }

    fun addNewFood(food: Food){
        foods.add(food)
        this.notifyDataSetChanged()
    }
}