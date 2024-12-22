package com.mpmscmr.calculadorakotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.PI

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

        val operadoresBasicos = listOf(
            findViewById<Button>(R.id.btnsum),
            findViewById<Button>(R.id.resbtn),
            findViewById<Button>(R.id.prodbtn),
            findViewById<Button>(R.id.divbtn)
        )

        val operadoresTrigonometricos = listOf(
            findViewById<Button>(R.id.senbtn),
            findViewById<Button>(R.id.cosbtn),
            findViewById<Button>(R.id.tanbtn)
        )

        val igual = findViewById<Button>(R.id.equalbtn)
        val clear = findViewById<Button>(R.id.cbtn)

        igual.setOnClickListener {
            try {
                if (operacionText.contains("/0")) {
                    resultado.text = "Syntax Error, no se puede dividir para 0"
                    return@setOnClickListener
                }

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

        operadoresBasicos.forEach { operador ->
            operador.setOnClickListener {
                if (operacionText.isNotEmpty() && (operacionText.last().isDigit() || operacionText.last() == ')')) {
                    operacionText += operador.text
                    operacion.text = operacionText
                } else {
                    operacionText = operacionText.dropLast(1) + operador.text
                    operacion.text = operacionText
                }
            }
        }

        operadoresTrigonometricos.forEach { operador ->
            operador.setOnClickListener {
                try {
                    if (operacionText.isNotEmpty() && (operacionText.last().isDigit() || operacionText.last() == ')')) {
                        igual.performClick()
                        val angleInDegrees = resultado.text.toString().toDouble()
                        val angleInRadians = Math.toRadians(angleInDegrees)
                        operacionText = "${operador.text}($operacionText)"
                        operacion.text = operacionText
                        when (operador.text) {
                            "sen" -> resultado.text = Math.sin(angleInRadians).toString()
                            "cos" -> resultado.text = Math.cos(angleInRadians).toString()
                            "tan" -> resultado.text = Math.tan(angleInRadians).toString()
                        }
                    }
                } catch (e: Exception) {
                    resultado.text = "Error: ${e.message}"
                }
            }
        }

        clear.setOnClickListener {
            try {
                if (operacionText.last() == ')') {
                    operacionText = operacionText.dropLast(1)
                    operacionText = operacionText.dropWhile { it != '(' }
                    operacionText = operacionText.drop(1)
                } else operacionText = operacionText.dropLast(1)

                operacion.text = operacionText
                if (operacionText.last().isDigit() || operacionText.last() == ')') igual.performClick()

            } catch (e: Exception){
                resultado.text = ""
            }
        }

    }
}
