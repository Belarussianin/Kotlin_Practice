package cleverTechTest

import cleverTechTest.classes.*
import java.lang.System.`in`
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

val input = Scanner(`in`)

data class Point(
    val x: Int,
    val y: Int,
    val z: Int
)

infix fun Int.calculateDistance(a: Int) = max(this, a) - min(this, a)

infix fun Point.calculateDistance(point: Point): Double {
    val x = x calculateDistance point.x
    val y = y calculateDistance point.y
    val z = z calculateDistance point.z
    return sqrt((x * x).toDouble() + (y * y).toDouble() + (z * z).toDouble())
}

fun main() {
    val first = Point(-1, 2, 4)
    val second = Point(5, 2, 1)
    val third = Point(5, 12, -2)
    println("Points:")
    println("First point: $first\nSecond point: $second\nThird point: $third")
    println("\nDistance from first to second: ${first calculateDistance second}")
    println("Distance from second to third: ${second calculateDistance third}")
    println("\nNo obstacles on the way, safe trip! XD")

    println()

    val commands = listOf(
        "list" to "print list of zoo animals", "help" to "enter command or something else to exit"
    )
    println(
        "Enter \"list\" command to print list of zoo animals or something else to exit, AFTER CONTINUE.\nContinue Y/N ?"
    )
    if (!input.nextLine().equals("y", true)) return
    val zoo = Zoo().apply {
        addAnimals(4) { Duck() }
        addAnimals(7) { Colibri() }
        addAnimals(3) { Bear() }
        addAnimals(5) { Wolf() }
        addAnimal(Peacock())
        addAnimals(2) { Beaver() }
        printAnimalsList()
        open()
    }
    while (true) {
        val temp = input.nextLine()
        zoo.close()
        commands.forEach {
            println(it)
        }
        println("Enter command:")
        if (!input.nextLine().contains("list", true)) return
        zoo.printAnimalsList()
        zoo.open()
    }
}

//Задание 1
//
//Даны координаты трех точек в трехмерной прямоугольной системе координат.
// Найдите расстояние, которое необходимо преодолеть для того, чтобы переместиться из первой точки во вторую,
// а потом из второй в третью при условии, что между ними нет препятствий.
//
//
//Задание 2
//
//При помощи объектно-ориентированного подхода создать свой маленький кусочек дикой природы.
// В нём должны присутствовать, как минимум, 4 утки, 7 колибри, 3 медведя, 5 волков, павлин и 2 бобра.
// Все обитатели должны уметь делать базовые вещи такие как ходить, летать, есть (если это могут делать их прототипы из реальной жизни, например, очевидно, что медведь не умеет летать, а колибри ходить).
// Также необходимо придумать и реализовать механизм контроля текущего занятия животного (т.е. того, чем оно сейчас занимается: ест, спит, перемещается, охотится и т.п.).
//
//
//Язык программирования для выполнения тестового задания:
//Android -  Java и Kotlin;