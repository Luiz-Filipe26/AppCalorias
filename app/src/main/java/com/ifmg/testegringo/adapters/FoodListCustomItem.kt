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
import com.ifmg.testegringo.control.FoodControl
import com.ifmg.testegringo.databinding.ActivityHome2Binding
import com.ifmg.testegringo.model.Food

class FoodListCustomItem(var context: Context, private val foodControl: FoodControl, val bindingHome: ActivityHome2Binding) :
    BaseAdapter() {

    lateinit var itemAdapter: ArrayAdapter<String>
    var foods: MutableList<Food> = mutableListOf()
    lateinit var inflater: LayoutInflater

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
        if (tempView == null) {
            tempView = inflater.inflate(R.layout.food_item_home, parent, false)
            val foodSelected = foods[position]
            tempView.findViewById<TextView>(R.id.name_item).setText(foodSelected.name)
            tempView.findViewById<TextView>(R.id.cal_item).setText(foodSelected.calories.toString())

            registerEvents(tempView, position)

            return tempView
        } else {
            return tempView
        }

    }

    private fun registerEvents(tempView: View, position: Int) {
        tempView.findViewById<ImageButton>(R.id.hide_food).setOnClickListener {
            tempView.findViewById<ConstraintLayout>(R.id.container_item_food)
                .setBackgroundColor(Color.RED)
        }

        tempView.findViewById<ImageButton>(R.id.delete_food).setOnClickListener {
            val food = foods[position]
            foodControl.removeFood(food)
            foods.removeAt(position)
            this.notifyDataSetChanged()
            bindingHome.dailyCaloriesTxt.setText("Calorias: ${foodControl.getTotalCaloreies()}")
        }
    }

    fun addNewFood(food: Food) {
        foods.add(food)
        this.notifyDataSetChanged()
    }

    fun addFoods(newFoods: List<Food>) {
        foods.clear()
        foods.addAll(newFoods)
        this.notifyDataSetChanged()
    }
}