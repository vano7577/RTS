package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun lab1(view: View){
        val editText = findViewById<EditText>(R.id.EnterNLab31)
        val aResult = findViewById<TextView>(R.id.aResultLab31)
        val bResult = findViewById<TextView>(R.id.bResult)
        val number = editText.text.toString().toInt()
        val errorMessage = findViewById<TextView>(R.id.errorMessage)

        var err =""
        if (number % 2 == 0) {
            errorMessage.apply {
                text = "Enter an odd number"
            }
        }
        else if  (number <= 0) {
            errorMessage.apply {
                text = "Enter a positive number"
            }
        }
        else {
            val result = lab3_1().gen_alg(number)
            aResult.apply { text= result[0].toString() }
            bResult.apply { text= result[1].toString()}
            errorMessage.apply {
                text = "no errors"
            }
        }
    }
    fun lab2(view: View){
        val spinnerSpeed = findViewById<Spinner>(R.id.speed)
        val spinnerTime = findViewById<Spinner>(R.id.time)
        val spinnerIter = findViewById<Spinner>(R.id.iter)
        val w1Result =  findViewById<TextView>(R.id.w1Result)
        val w2Result =  findViewById<TextView>(R.id.w2Result)
        val selectedSpeed = spinnerSpeed.selectedItem.toString().toDouble()
        val selectedTime = spinnerTime.selectedItem.toString().toDouble()
        val selectedIter = spinnerIter.selectedItem.toString().toInt()
        val result = lab_3_2(selectedSpeed,selectedTime,selectedIter).find_answer()
        val errorMessage = findViewById<TextView>(R.id.errorMessage)
        val illegalValues = listOf(
            Double.NaN,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY
        )
        if (result.first in illegalValues || result.second in illegalValues) {
            errorMessage.apply {
                text = "Solution couldn`t be found"
                }
        }else{
            w1Result.apply { text= result.first.toString() }
            w2Result.apply { text= result.second.toString() }
            errorMessage.apply {
                text = "no errors"
            }
        }

    }
    fun lab3(view: View){
        val editX1 = findViewById<EditText>(R.id.editx1).text.toString().toDouble()
        val editX2 = findViewById<EditText>(R.id.editx2).text.toString().toDouble()
        val editX3 = findViewById<EditText>(R.id.editx3).text.toString().toDouble()
        val editX4 = findViewById<EditText>(R.id.editx4).text.toString().toDouble()
        val editY = findViewById<EditText>(R.id.edity).text.toString().toDouble()
        val errorMessage = findViewById<TextView>(R.id.errorMessage)
        val resultA =  findViewById<TextView>(R.id.aResultLab33)
        val resultB =  findViewById<TextView>(R.id.bResultLab33)
        val resultC =  findViewById<TextView>(R.id.cResultLab33)
        val resultD =  findViewById<TextView>(R.id.dResultLab33)
        val result = lab3_3(editX1,editX2,editX3,editX4,editY).find_answer()
        if (result.isEmpty()) {
            errorMessage.apply {
                text = "No possible answer in range [1;y/2]"
            }
        }else{
            resultA.apply {text= result[0].toString()}
            resultB.apply {text= result[1].toString()}
            resultC.apply {text= result[2].toString()}
            resultD.apply {text= result[3].toString()}
            errorMessage.apply {
                text = "no errors"
            }
        }
    }
}
fun lab3_1(n : Int = 1) : MutableList<Int> {
    var n : Int =899
    if(n % 2 == 0) error("Введите нечётное число")
    if (n <= 0) error("Введите положительное число")
    var x : Int = sqrt(n.toDouble()).toInt()+1
    var k : Int = 0
    var i : Int =0
    var y : Int = 0
    while (k==0){
        y= Math.pow(((x + i).toDouble()), 2.00).toInt()-n
        if((sqrt(y.toDouble()))% 1.0 == 0.0){
            k=i
            break
        }else i++
    }
    var a : Int =x+k
    var b : Int = sqrt(y.toDouble()).toInt()
    println(a)
    println(b)
    println((a+b)*(a-b))
    return mutableListOf(a,b)
}