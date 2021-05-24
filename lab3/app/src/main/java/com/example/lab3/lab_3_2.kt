package com.example.lab3

class lab_3_2
constructor(in_speed: Double, in_time: Double, in_iter: Int) {
    var speed = in_speed
    var time = in_time
    var iter = in_iter
    private var W1 = 0.00
    private var W2 = 0.00
    private var P = 4.00
    var points = arrayListOf(Pair(0.00, 6.00), Pair(1.00, 5.00), Pair(3.00, 3.00), Pair(2.00, 4.00))
    private fun validate(): Boolean {
        val y1 = W1 * points[0].first + W2 * points[0].second
        val y2 = W1 * points[1].first + W2 * points[1].second
        val y3 = W1 * points[2].first + W2 * points[2].second
        val y4 = W1 * points[3].first + W2 * points[3].second
        if ((y1 > P) && (y2 > P) && (y3 < P) && (y4 < P)) {
            return true
        }
        return false
    }

    fun find_answer(): Pair<Double, Double> {
        val time_in = System.currentTimeMillis()
        for (i in 0..iter) {
            if ((System.currentTimeMillis() - time_in) <= time * 1000) {
                for (k in 0 until points.size) {
                    val y = W1 * points[k].first + W2 * points[k].second
                    val delta = P - y
                    W1 += delta * points[k].first * speed
                    W2 += delta * points[k].second * speed
                    if (validate()) {
                        return Pair(W1, W2)
                    }
                }
            }
        }
        return Pair(W1, W2)
    }
}
//
//fun main() {
//    val example2 = lab_3_2(0.01, 0.5, 100)
//    println(example2.find_answer())
//}