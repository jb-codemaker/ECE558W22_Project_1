/*

Student: Joshua Blazek
Class: ECE558 - Winter '22

Project #1: Calculator

Resources Used:
https://developer.android.com/
Smythe, Neil - Android Studio Arctic Fox Essentials - Kotlin Edition-Payload Media (2021)
Professor Kravitz Class Handouts

Original Source: Professor Kravitz OhmsLawCalculator v_2.0

 */


package edu.pdx.jblazek.ece558w22Project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import edu.pdx.jblazek.ece558w22Project1.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"

    private lateinit var binding:ActivityMainBinding    // reference to view binding object

    // Lambdas to implement calculator
    val calcAdd:    (Double, Double)->Double = {x: Double, y: Double -> x + y}
    val calcSubtract:    (Double, Double)->Double = {x: Double, y: Double -> x - y}
    val calcMultiply:    (Double, Double)->Double = {x: Double, y: Double -> x * y}
    val calcDivide:    (Double, Double)->Double = {x: Double, y: Double -> x / y}
    val calcPercent:    (Double, Double)->Double = {x: Double, _: Double -> x / 100.0}
    val calcSquareRoot:    (Double, Double)->Double = {x: Double, _: Double -> sqrt(abs(x)) }
    var result = 0.0
    //validInputs = op1, op2
    var validInputs = booleanArrayOf(false, false)
    var op1String = ""
    var op2String = ""

    //Check for valid input
    fun validInputs(){
        // operands must not be empty or blank and have a value > 0.0
        validInputs[0] = (op1String.isNotBlank() && op1String.isNotEmpty() && op1String.toDoubleOrNull() != null)
        validInputs[1] = (op2String.isNotBlank() && op2String.isNotEmpty() && op2String.toDoubleOrNull() != null)
    }

    //update String values
    fun updateString(){
        op1String = binding.op1Value.text.toString().trim()
        op2String = binding.op2Value.text.toString().trim()
    }

    //Take in selection and output result to screen
    fun results(selection: String){
        //Update string values
        updateString()
        //Update valid inputs
        validInputs()
        Log.d(TAG, "*CALCULATE*")

        if (selection == "calcAdd" && validInputs[0] && validInputs[1]) {
            result = calcAdd(op1String.toDouble(), op2String.toDouble())
        }
        else if (selection == "calcSubtract" && validInputs[0] && validInputs[1]) {
            result = calcAdd(op1String.toDouble(), op2String.toDouble())
        }
        else if (selection == "calcMultiply" && validInputs[0] && validInputs[1]) {
            result = calcAdd(op1String.toDouble(), op2String.toDouble())
        }
        else if (selection == "calcDivide" && validInputs[0] && validInputs[1]) {
            result = calcAdd(op1String.toDouble(), op2String.toDouble())
        }
        else if (selection == "calcPercent" && validInputs[0]) {
            result = calcAdd(op1String.toDouble(), 0.0)
        }
        else if (selection == "calcSquareRoot" && validInputs[0]) {
            result = calcAdd(op1String.toDouble(), 0.0)
        }
        else {
            binding.resultText.text = ""
            binding.op1Value.setText("")
            binding.op2Value.setText("")
            Toast.makeText(this, getString(R.string.invalid_operand_message), Toast.LENGTH_SHORT).show()
        }
        //Update results content
        binding.resultText.text = result.toString()
        Log.d(TAG, "First Operand: $op1String, Second Operand: $op2String,  Calculated result: $result" )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add
        binding.addBtn.setOnClickListener {
            results("calcAdd")
        }
        // Subtract
        binding.subtractBtn.setOnClickListener {
            results("calcSubtract")
        }
        // Multiply
        binding.multiplyBtn.setOnClickListener {
            results("calcMultiply")
        }
        // Divide
        binding.divideBtn.setOnClickListener {
            results("calcDivide")
        }
        // Percent
        binding.percentBtn.setOnClickListener {
            results("calcPercent")
        }
        // Square Root
        binding.squareRootBtn.setOnClickListener {
            results("calcSquareRoot")
        }
    }
}