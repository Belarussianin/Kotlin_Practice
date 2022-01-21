package cleverTechTest.classes

import cleverTechTest.AnimalState
import cleverTechTest.basicClasses.Animal
import cleverTechTest.basicClasses.Mammal

class Bear(initState: AnimalState? = null) : Mammal(initState) {
    override fun makeAnimal(initState: AnimalState?): Animal = Bear(initState)
}