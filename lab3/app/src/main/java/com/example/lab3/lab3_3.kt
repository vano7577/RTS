package com.example.lab3

import kotlin.math.absoluteValue

class lab3_3
constructor(in_x1: Double = 1.00, in_x2: Double = 1.00, in_x3: Double = 2.00, in_x4: Double = 0.00, in_y: Double = 15.00) {
    val x1 = in_x1
    val x2 = in_x2
    val x3 = in_x3
    val x4 = in_x4
    val y = in_y
    val zero_population: MutableList<MutableList<Double>> = mutableListOf()
    var population: MutableList<MutableList<Double>> = mutableListOf()
    val fitness_list: MutableList<Double> = mutableListOf()
    val child_popul: MutableList<MutableList<Double>> = mutableListOf()
    var best_popul: MutableList<Double> = mutableListOf()

    fun find_fitness(popul_row: MutableList<Double>): Double {
        val fitness: Double = y -
                popul_row[0] * x1 -
                popul_row[1] * x2 -
                popul_row[2] * x3 -
                popul_row[3] * x4
        return fitness.absoluteValue
    }

    fun create_zero_population() {
        for (i in 0..3) {
            zero_population.add(mutableListOf())
            for (j in 0..3) {
                zero_population[i].add((1..8).random().toDouble())
            }
        }
    }

    fun find_fitness_of_popul() {
        fitness_list.clear()
        if (population.isEmpty()) {
            zero_population.mapTo(population) { it }
        }
        for (i in 0..3) {
            fitness_list.add(find_fitness(this.population[i]))
        }
    }

    fun play_rulet() {
        child_popul.clear()
        var rulet = 0.00
        val procent_rulet: MutableList<Double> = mutableListOf()
        val rulet_circle: MutableList<Double> = mutableListOf()
        this.fitness_list.forEach { rulet += 1 / it }
        for (i in 0..3) {
            procent_rulet.add(1 / fitness_list[i] / rulet)
        }

        for (i in 0..3) {
            if (i == 0) {
                rulet_circle.add(procent_rulet[i])
            } else {
                rulet_circle.add(rulet_circle[i - 1] + procent_rulet[i])
            }
        }
        var i = 0
        child_popul.clear()
        while (i < 4) {
            val piu: Double = (1..100).random().toDouble() / 100
            var k = 0
            var this_child = 0
            for (k in 0..3) {
                if (piu >= rulet_circle[k]) {
                    this_child = k
                }
            }
            child_popul.add(population[this_child])
            i++
        }
    }

    fun crossing_over() {
        population.clear()
        for (p in 0..3) {
            val c: MutableList<Double> = mutableListOf()
            c.clear()
            for (j in 0..3) {
                if (p % 2 == 0) {
                    if (j < 2) {
                        c.add(child_popul[p][j])
                    } else c.add(child_popul[p + 1][j])
                } else
                    if (j < 2) {
                        c.add(child_popul[p][j])
                    } else c.add(child_popul[p - 1][j])
            }
            population.add(c)
        }
    }

    fun find_best_fitness(): Boolean {
        this.find_fitness_of_popul()
        fitness_list.forEach { if (it == 0.0) return true }
        return false
    }

    fun life() {
        var q = 0
        while (!find_best_fitness() && q < 10) {
            this.find_fitness_of_popul()
            this.play_rulet()
            this.crossing_over()
            q++
        }
    }

    fun find_answer(): MutableList<Double> {
        this.create_zero_population()
        this.life()
        while ((!this.fitness_list.contains(0.0)) &&
            population[0] == child_popul[0] &&
            population[1] == child_popul[1] &&
            population[2] == child_popul[2] &&
            population[3] == child_popul[3]
        ) {
            zero_population.clear()
            population.clear()
            fitness_list.clear()
            child_popul.clear()
            this.create_zero_population()
            this.life()
        }
        for (i in 0..3) {
            if (fitness_list[i] == 0.0) {
                best_popul = population[i]
            }
        }
        return best_popul
    }
}
//
//fun main() {
//    val example = lab3_3()
//    println(example.find_answer())
//}