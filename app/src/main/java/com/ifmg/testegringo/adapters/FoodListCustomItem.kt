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
    private val views: MutableList<View> = mutableListOf()
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
        if (position < views.size) {
            val view = views[position]
            return views[position]
        }

        val view = inflater.inflate(R.layout.food_item_home, parent, false)
        val foodSelected = foods[position]
        view.findViewById<TextView>(R.id.name_item).text = foodSelected.name
        view.findViewById<TextView>(R.id.cal_item).text = foodSelected.calories.toString()
        registerEvents(view, position)
        views.add(view)

        return view
    }


    private fun registerEvents(tempView: View, position: Int) {
        tempView.findViewById<ImageButton>(R.id.hide_food).setOnClickListener {
            tempView.findViewById<ConstraintLayout>(R.id.container_item_food)
                .setBackgroundColor(Color.RED)
        }

        tempView.findViewById<ImageButton>(R.id.delete_food).setOnClickListener {

            val viewIndex = views.indexOf(tempView)
            if (viewIndex != -1) {
                val food = foods[viewIndex]
                foods.removeAt(viewIndex)
                views.removeAt(viewIndex)
                foodControl.removeFood(food)
                foodControl.removeTotalCalories(food.calories)
                this.notifyDataSetChanged()
                bindingHome.dailyCaloriesTxt.text = "Calorias: ${foodControl.getTotalCaloreies()}"
            }
        }
    }

    fun addNewFood(food: Food) {
        foods.add(food)
        this.notifyDataSetChanged()
    }

    fun addFoods(newFoods: List<Food>) {
        foods.clear()
        views.clear()
        foods.addAll(newFoods)
        this.notifyDataSetChanged()
    }
}