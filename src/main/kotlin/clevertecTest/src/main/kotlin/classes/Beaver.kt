package cleverTechTest.classes

import cleverTechTest.AnimalState
import cleverTechTest.basicClasses.Animal
import cleverTechTest.basicClasses.Mammal
import cleverTechTest.interfaces.Swimmable

class Beaver(initState: AnimalState? = null) : Mammal(initState), Swimmable {

    override fun makeAnimal(initState: AnimalState?): Animal = Beaver(initState)

    override fun swim() {
        state = AnimalState.SWIMMING
        printState()
    }
}