package com.ifmg.testegringo

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifmg.testegringo.control.FoodControl
import com.ifmg.testegringo.databinding.ActivityEntryRegisterBinding
import com.ifmg.testegringo.model.Food
import com.ifmg.testegringo.tools.Formatters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar

class EntryRegisterActivity : AppCompatActivity() {
    private lateinit var bindingEntry:ActivityEntryRegisterBinding
    private val dayFood:Calendar = Calendar.getInstance()

    private lateinit var foodControlEntry:FoodControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingEntry = ActivityEntryRegisterBinding.inflate(layoutInflater)

        setContentView(bindingEntry.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadInitialData()

        registerEvents()
    }

    private fun loadInitialData(){
        bindingEntry.dateTxt.setText(Formatters.dayFormatter(dayFood.time))

        bindingEntry.timeTxt.setText(Formatters.timeFormatter(dayFood.time))

    }

    private fun registerEvents(){
        bindingEntry.registBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        //configurando DatePicker para Data
        //registrando o DatePicker para o campo de data
        bindingEntry.dateBtn.setOnClickListener {
            val datePicker:DatePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener {
                   view, year, month, dayOfMonth ->

                dayFood.set(year, month, dayOfMonth)

                bindingEntry.dateTxt.setText("${dayOfMonth}/${month+1}/${year}")
            }, dayFood.get(Calendar.YEAR),
                dayFood.get(Calendar.MONTH),
                dayFood.get(Calendar.DAY_OF_MONTH))

            bindingEntry.dateBtn.setOnClickListener {
                datePicker.show()
            }
        }

        //para definir o hor. da refeic.
        bindingEntry.timeBtn.setOnClickListener {

            val timePicker:TimePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener {
                        view, hour, min ->

                    dayFood.set(Calendar.HOUR, hour)
                    dayFood.set(Calendar.MINUTE, min)

                    bindingEntry.timeTxt.setText("${hour}:${min}")
                }, dayFood.get(Calendar.HOUR),
                dayFood.get(Calendar.MINUTE),
                true)

            bindingEntry.timeBtn.setOnClickListener {
                timePicker.show()
            }

        }

        bindingEntry.registBtn.setOnClickListener {

            //act. default para encapsular os resultados para a Act. que estará no topo da pilha ao aplicar finish
            val results:Intent = Intent()

            val food:Food = Food(bindingEntry.nameTxt.text.toString(),
                bindingEntry.caloriesTxt.text.toString().toFloat(),
                dayFood.time)

            //val intant:Instant = dayFood.toInstant()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                results.putExtra("food", food)
            }else{
                //para devices mais antigos
                results.putExtra("food", Json.encodeToString(food))
            }

            setResult(Activity.RESULT_OK, results)
            finish()

        }

    }
}