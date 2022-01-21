package cleverTechTest.basicClasses

import cleverTechTest.AnimalState

abstract class Animal(
    var state: AnimalState? = null,
    var name: String? = null
) {
    abstract fun makeAnimal(initState: AnimalState? = null): Animal

    abstract fun randomAction(): Unit

    companion object {
        fun Animal.printState() {
            println("${this::class.java.simpleName}: name: \"${name ?: "noname"}\", status: \"${this.state?.name ?: "unknown"}\"")
        }
    }
}