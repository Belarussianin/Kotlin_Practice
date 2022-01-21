package cleverTechTest.classes

import cleverTechTest.AnimalState
import cleverTechTest.basicClasses.Animal
import cleverTechTest.basicClasses.Mammal

class Wolf(initState: AnimalState? = null) : Mammal(initState) {
    override fun makeAnimal(initState: AnimalState?): Animal = Wolf(initState)
}