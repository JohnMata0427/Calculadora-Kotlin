package com.mpmscmr.calculadorakotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val number1 = findViewById<TextView>(R.id.display)
        val number2 = findViewById<TextView>(R.id.display2)
        val result = findViewById<TextView>(R.id.resultado)

        var operation = ' '

        findViewById<Button>(R.id.btn0).setOnClickListener {
            if (operation == ' ') {
                number1.text = "0"
            } else {
                number2.text = "0"
            }
        }

        findViewById<Button>(R.id.btn1).setOnClickListener {
            if (operation == ' ') {
                number1.text = "1"
            } else {
                number2.text = "1"
            }
            number1.text = "1"
        }

        findViewById<Button>(R.id.btnsum).setOnClickListener {
            operation = "suma".single()
        }
        
        findViewById<Button>(R.id.equalbtn){
            if (operation == "suma".single()){
                result.text = (number1.text.toString().toInt() + number2.text.toString().toInt()).toString()
            }
        }





    }

    private fun <T> findViewById(equalbtn: Int, function: () -> Unit) {

    }
}
