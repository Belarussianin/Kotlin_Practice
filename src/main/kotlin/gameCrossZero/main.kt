package gameCrossZero

import gameCrossZero.GameBotData.Mode
import gameCrossZero.GameData.Companion.indexIntoPosition
import gameCrossZero.GameData.Companion.makeEmptyGameField
import gameCrossZero.GameData.Companion.positionIntoIndex
import gameCrossZero.GameData.Player
import java.util.Scanner

val input = Scanner(System.`in`)

fun main() {
    //GameCrossZero.startGameWithBot()
    //GameCrossZero.test()
    testGame()
}

fun startGameWithBot() {
    val game = Game()
    val botPlayer = Player.X
    val bot = GameBot(game, Mode.HARD, botPlayer)

    gameHelp()
    when (botPlayer) {
        Player.X -> {
            while (game.getState() == GameData.GameState.GAME) {
                val botMove = bot.getIndexToMoveOrNull() ?: break
                println("\nBot turn index: $botMove")
                bot.makeMove()
                if (game.getState() != GameData.GameState.GAME) break
                println("Enemy turn index: ")
                game.makeTurn(input.nextInt())
            }
        }
        Player.O -> {
            while (game.getState() == GameData.GameState.GAME) {
                println("Enemy turn index: ")
                game.makeTurn(input.nextInt())
                val botMove = bot.getIndexToMoveOrNull() ?: break
                println("\nBot turn index: $botMove")
                bot.makeMove()
            }
        }
    }
}

fun gameHelp() = println("Indexes of field(num == index + 1)\n|0|1|2|\n|3|4|5|\n|6|7|8|")

fun testGame() {
    val game = Game(field = makeEmptyGameField(20), mode = GameData.GameMode.FIVE_TO_FIVE)
    var cancelGame = false

    while (!cancelGame) {
        game.getField().print()
        println("\n\"${game.getCurrentPlayer()}\" turn index: ")
        game.makeTurn(input.nextInt())
        if (game.getState() != GameData.GameState.GAME) {
            game.getField().print()
            println("Exit ? Y/N")
            cancelGame = input.next().equals("y", true)
            if (!cancelGame) game.reStart()
        }
    }
}

fun test() {
    println(indexIntoPosition(20, 4).toString())
    println(positionIntoIndex(2 to 2, 3).toString())
    println(makeEmptyGameField(3).print())
    println(arrayOf(1, 2, 3, 4, 5, 6, 7).contains(arrayOf(7, 6)))
}