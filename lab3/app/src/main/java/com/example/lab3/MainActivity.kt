package com.example.lab3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Collections.min


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    @SuppressLint("SetTextI18n")
    fun lab1(view: View) {
        val editText = findViewById<EditText>(R.id.EnterNLab31)
        val aResult = findViewById<TextView>(R.id.aResultLab31)
        val bResult = findViewById<TextView>(R.id.bResult)
        val number = editText.text.toString().toInt()
        val errorMessage = findViewById<TextView>(R.id.errorMessage)
        val time: Int

        when {
            number % 2 == 0 -> {
                errorMessage.apply {
                    text = "Enter an odd number"
                }
            }
            number <= 0 -> {
                errorMessage.apply {
                    text = "Enter a positive number"
                }
            }
            else -> {
                val time_in = System.currentTimeMillis()
                val result = lab3_1().gen_alg(number)
                time = (System.currentTimeMillis() - time_in).toInt()
                aResult.apply { text= result[0].toString() }
                bResult.apply { text= result[1].toString()}
                errorMessage.apply {
                    text = "no errors"
                }
                Toast.makeText(applicationContext , "$time nanosecond", Toast.LENGTH_LONG).show()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun lab2(view: View) {
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
    @SuppressLint("SetTextI18n")
    fun lab3(view: View) {
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
        val resultChance= findViewById<TextView>(R.id.chanceResultLab33)
        val result = lab3_3(editX1,editX2,editX3,editX4,editY).find_answer(0.15)
        val res_chance: MutableList<Double> = mutableListOf()
        var k =0
        for (j in 10..100) {

            var resultc = lab3_3(editX1, editX2, editX3, editX4, editY).find_answer(j/100.0)
            while(resultc.size==1){
                resultc = lab3_3(editX1, editX2, editX3, editX4, editY).find_answer(j/100.0)
            }
            res_chance.add(resultc[4])
            k++
        }
        var min=0
        var min_res=res_chance[0]
        for (i in res_chance.indices){
                if (min_res>res_chance[i])
                {
                    min_res=res_chance[i]
                    min=i
                }
            }

        if (result.size==1) {
            errorMessage.apply {
                text = "No possible answer in range [1;y/2]"
            }
        }else{

            resultChance.apply {  text =(min+10).toString()+'%' }
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

