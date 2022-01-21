package cleverTechTest.basicClasses

import cleverTechTest.AnimalState
import cleverTechTest.interfaces.Eatable
import cleverTechTest.interfaces.Feedable
import cleverTechTest.interfaces.Moveable
import cleverTechTest.interfaces.Sleepable

abstract class Mammal(
    initState: AnimalState? = null
) : Animal(initState), Feedable, Moveable, Sleepable, Eatable {

    override fun randomAction() {
        when ((1..4).random()) {
            1 -> feed()
            2 -> eat()
            3 -> sleep()
            4 -> move()
        }
    }

    override fun feed() {
        state = AnimalState.FEEDING
        printState()
    }

    override fun eat() {
        state = AnimalState.EATING
        printState()
    }

    override fun move() {
        state = AnimalState.MOVING
        printState()
    }

    override fun sleep() {
        state = AnimalState.SLEEPING
        printState()
    }
}