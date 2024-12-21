package com.mpmscmr.calculadorakotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import net.objecthunter.exp4j.ExpressionBuilder

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

        var operacionText = ""

        val operacion = findViewById<TextView>(R.id.operacion)
        val resultado = findViewById<TextView>(R.id.resultado)

        val numeros = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9),
        )

        val suma = findViewById<Button>(R.id.btnsum)
        val resta = findViewById<Button>(R.id.resbtn)
        val producto = findViewById<Button>(R.id.prodbtn)
        val division = findViewById<Button>(R.id.divbtn)

        val coseno = findViewById<Button>(R.id.cosbtn)

        val igual = findViewById<Button>(R.id.equalbtn)
        val clear = findViewById<Button>(R.id.cbtn)

        igual.setOnClickListener {
            try {
                // Verificar que los paréntesis estén balanceados
                if (operacionText.count { it == '(' } == operacionText.count { it == ')' }) {
                    val resultadoOperacion = ExpressionBuilder(operacionText).build().evaluate().toString()
                    resultado.text = resultadoOperacion
                } else {
                    resultado.text = "Error: Paréntesis no balanceados"
                }
            } catch (e: Exception) {
                resultado.text = "Error: ${e.message}"
            }
        }

        numeros.forEach { numero ->
            numero.setOnClickListener {
                operacionText += numero.text
                operacion.text = operacionText
                igual.performClick()
            }
        }

        producto.setOnClickListener {
            if (operacionText.isNotEmpty() && operacionText.last().isDigit()) {
                operacionText += "*"
                operacion.text = operacionText
            } else {
                operacionText = operacionText.dropLast(1) + "*"
                operacion.text = operacionText
            }
        }

        coseno.setOnClickListener {
            // La funcion coseno funciona primero ingresando el numero y luego la función para poder calcularlo
            if (operacionText.isNotEmpty() && operacionText.last().isDigit()) {
                operacionText = "cos($operacionText)"
                operacion.text = operacionText
            }
        }

        clear.setOnClickListener {
            operacionText = operacionText.dropLast(1)
            operacion.text = operacionText
            if (operacionText.last().isDigit()) igual.performClick()
        }

    }
}
