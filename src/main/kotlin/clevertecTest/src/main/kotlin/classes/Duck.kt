package cleverTechTest.classes

import cleverTechTest.AnimalState
import cleverTechTest.basicClasses.Animal
import cleverTechTest.basicClasses.Bird

class Duck(initState: AnimalState? = null) : Bird(initState) {
    override fun makeAnimal(initState: AnimalState?): Animal = Duck(initState)
}