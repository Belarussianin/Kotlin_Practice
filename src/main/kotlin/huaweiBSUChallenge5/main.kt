package huaweiBSUChallenge5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Object as Object1

fun <T : Any> className(Class: T) = Class.javaClass.name.substringAfterLast('.')

class Person(
    private val name: String
) {
    var age: Int = 0

    var lol = List<Int>(1) { 4 }

    init {
        println("New ${className(this)} with name = \"$name\"")
    }

    constructor(name: String, age: Int) : this(name) {
        println("New ${className(this)} with name = \"$name\" and age = \"$age\"")
        this.age = age
    }
}

fun Int.reversed(): Int = try {
    if (this < 0) this.toString().substringAfterLast('-').reversed().toInt() * -1 else this.toString().reversed()
        .toInt()
} catch (ex: Exception) {
    0
}

//@OptIn(ExperimentalCoroutinesApi::class)
//fun main() {
//    val lol = flowOf("1", "2", "3")
//    val lol2 = flowOf("a", "b", "c")
//
//    CoroutineScope(Dispatchers.Unconfined).launch {
//        combine(lol, lol2) { first, second ->
//            first + second
//        }.collect {
//            println(it)
//        }
//    }
////    CoroutineScope(Dispatchers.Unconfined).launch {
////        lol.collect {
////            println(it)
////        }
////    }
//
////    println(1234.reversed())
////    Person("Arseni")
////    Person("Anya", 27)
//    //println(reverseBits(43261596u))
//}

fun reverseBits(n: UInt): UInt {
    val num = n.toString(2).reversed().toMutableList()
    while (num.size < 32) {
        num.add('0')
    }
    for (i in num.indices) {
        num[i] = (num[i].code xor 0).toChar()
    }
    return num.joinToString("", "", "").toUInt(2)
}

sealed class DataState<T>(open val data: T? = null, val message: String? = null) where T : Any? {
    class Ready<T>(override val data: T) : DataState<T>(data)
    class Error<T>(message: String) : DataState<T>(message = message)
    class Loading<T> : DataState<T>()
}

fun main() {

}