package com.example.lab3

import kotlin.math.pow
import kotlin.math.sqrt

class lab3_1 {
    fun gen_alg(n: Int): MutableList<Int> {
        val x: Int = sqrt(n.toDouble()).toInt() + 1
        var k = 0
        var i = 0
        var y = 0
        while (k == 0) {
            y = ((x + i).toDouble()).pow(2.00).toInt() - n
            if ((sqrt(y.toDouble())) % 1.0 == 0.0) {
                k = i
                break
            } else i++
        }
        val a: Int = x + k
        val b: Int = sqrt(y.toDouble()).toInt()
        return mutableListOf(a, b)
    }
}