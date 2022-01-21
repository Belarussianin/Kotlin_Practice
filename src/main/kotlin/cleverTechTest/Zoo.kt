package cleverTechTest

import cleverTechTest.basicClasses.Animal
import cleverTechTest.basicClasses.Animal.Companion.printState
import kotlinx.coroutines.*

class Zoo {
    private val scope = GlobalScope
    private var currentJob: Job? = null
    private val animals = mutableListOf<Animal>()

    fun open() {
        currentJob = scope.launch {
            while (true) {
                animals[animals.indices.random()].randomAction()
                delay(1000)
            }
        }
    }

    fun close() {
        currentJob?.cancel()
    }

    fun addAnimal(animal: Animal) = animals.add(animal)

    inline fun addAnimals(count: Int, initBlock: (Int) -> Animal) {
        repeat(count) {
            addAnimal(initBlock(it))
        }
    }

    fun printAnimalsList() {
        println("Zoo:")
        repeat(50) {
            print("-")
        }
        println()
        for (animal in animals) {
            animal.printState()
        }
        repeat(50) {
            print("-")
        }
        println()
    }
}