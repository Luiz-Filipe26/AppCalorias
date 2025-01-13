package com.ifmg.testegringo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifmg.testegringo.adapters.FoodListAdapter
import com.ifmg.testegringo.adapters.FoodListCustomItem
import com.ifmg.testegringo.control.FoodControl
import com.ifmg.testegringo.databinding.ActivityHome2Binding
import com.ifmg.testegringo.model.Food
import kotlinx.serialization.json.Json
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var bindingHome: ActivityHome2Binding
    private lateinit var foodControl: FoodControl

    private var countFood: Int = 0

    lateinit var newFoodCallback: ActivityResultLauncher<Intent>

    //lateinit var adapterFoodsList:FoodListAdapter
    lateinit var adapterFoodsListCustom: FoodListCustomItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingHome = ActivityHome2Binding.inflate(layoutInflater)

        setContentView(bindingHome.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foodControl = FoodControl(this)

        /*adapterFoodsList = FoodListAdapter(this)
        bindingHome.foodsList.adapter = adapterFoodsList.itemAdapter*/
        adapterFoodsListCustom = FoodListCustomItem(this)
        bindingHome.foodsList.adapter = adapterFoodsListCustom

        loadCacheData()

        registerCallbacks()

        registerEvents()

    }

    private fun loadCacheData() {
        bindingHome.dailyCaloriesTxt.setText("Calorias: ${foodControl.getTotalCaloreies()}")
    }

    private fun registerCallbacks() {
        newFoodCallback =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result -> foodCallBack(result) })
    }

    private fun foodCallBack(result: ActivityResult) {
        print("retorno da Activity anterior")
        val datas: Bundle? = result.data?.extras

        if (datas == null || !datas.containsKey("food")) {
            return
        }

        /*val calories:Float = datas.getFloat("calories")*/

        val food: Food? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                datas.getSerializable("food", Food::class.java)
            }
            //api mais antiga (devices antigos)
            else if (datas.getString("food") != null) {
                Json.decodeFromString(datas.getString("food")!!) as Food
            } else {
                null
            }

        if (food == null) {
            return
        }

        val changedDay = foodControl.setLastDay(Calendar.getInstance().time)
        if (changedDay) {
            countFood = 0
        }

        foodControl.addTotalCalories(food.calories)

        countFood += food.calories.toInt()

        bindingHome.dailyCaloriesTxt.setText("Calorias: ${countFood}")


        //cadastra em banco
        val id = foodControl.registerFoodDatabase(food)
        Toast.makeText(this, "id:${id}", Toast.LENGTH_SHORT).show()

        adapterFoodsListCustom.addNewFood(food)

    }

    private fun registerEvents() {
        bindingHome.addFoodBtn.setOnClickListener(View.OnClickListener {
            val transitionRegister: Intent = Intent(this, EntryRegisterActivity::class.java)

            newFoodCallback.launch(transitionRegister)

            //startActivity(transitionRegister)

        })

        //selecao de um item (GUI) da lista mapeada como Adapter
        bindingHome.foodsList.setOnItemClickListener { parent, view, position, id ->

            //adapterFoodsList.removeFood(position)

        }


    }


    /*override fun onRestart() {
        super.onRestart()

        countFood++

        bindingHome.dailyCaloriesTxt.setText("Calorias: ${countFood}")

        foodControl.addTotalCalories(1.0f)
    }*/
}