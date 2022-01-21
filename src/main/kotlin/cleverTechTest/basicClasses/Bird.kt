package cleverTechTest.basicClasses

import cleverTechTest.AnimalState
import cleverTechTest.interfaces.*

abstract class Bird(
    initState: AnimalState? = null
) : Animal(initState), Flyable, Moveable, Sleepable, Eatable {

    override fun randomAction() {
        when ((1..4).random()) {
            1 -> fly()
            2 -> eat()
            3 -> sleep()
            4 -> move()
        }
    }

    override fun fly() {
        state = AnimalState.FLYING
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