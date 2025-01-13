package com.ifmg.testegringo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifmg.testegringo.databinding.ActivityMainBinding

lateinit var bindingMain:ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //é como se tiv. um "balão" (um layout que referencia TODOS os elementos)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bindingMain.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerEvents()
    }

    private fun registerEvents(){
        bindingMain.loginBtn.setOnClickListener(View.OnClickListener {
            val transitionHome:Intent = Intent(this, HomeActivity::class.java)

            startActivity(transitionHome)

            //não emp. a Activity Atual
            finish()


        })
    }


}