package com.example.lab3

import kotlin.math.sqrt

class lab3_1 {
    public fun gen_alg(n: Int): MutableList<Int> {
        val x: Int = sqrt(n.toDouble()).toInt() + 1
        var k: Int = 0
        var i: Int = 0
        var y: Int = 0
        while (k == 0) {
            y = Math.pow(((x + i).toDouble()), 2.00).toInt() - n
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