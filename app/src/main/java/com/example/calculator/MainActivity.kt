package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var inputScreen: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputScreen = findViewById(R.id.inputScreen)
    }
    fun onDigit(view: View){
        inputScreen?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        inputScreen?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot){
            inputScreen?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View){
        inputScreen?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                inputScreen?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var inputValue = inputScreen?.text.toString()
            var prefix = ""

            try {
                if(inputValue.startsWith("-")) {
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }
                if(inputValue.contains("-")) {
                    val splitValue = inputValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inputScreen?.text = removeDotZero((one.toDouble() - two.toDouble()).toString())
                }
//                else if(inputValue.contains("-")) {
//                    val splitValue = inputValue.split("-")
//                    var one = splitValue[0]
//                    var two = splitValue[1]
//
//                    if(prefix.isNotEmpty()){
//                        one = prefix + one
//                    }
//
//                    inputScreen?.text = removeDotZero((one.toDouble() - two.toDouble()).toString())
//                }
                else if(inputValue.contains("+")) {
                    val splitValue = inputValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inputScreen?.text = removeDotZero((one.toDouble() + two.toDouble()).toString())
                }else if(inputValue.contains("/")) {
                    val splitValue = inputValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inputScreen?.text = removeDotZero((one.toDouble() / two.toDouble()).toString())
                }else if(inputValue.contains("*")) {
                    val splitValue = inputValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    inputScreen?.text = removeDotZero((one.toDouble() * two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeDotZero(result: String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }


    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")
        }
    }
}