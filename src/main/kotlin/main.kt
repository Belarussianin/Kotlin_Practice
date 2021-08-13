import GameBotData.Mode
import GameData.Companion.indexIntoPosition
import GameData.Companion.makeEmptyGameField
import GameData.Companion.positionIntoIndex
import GameData.Player
import java.util.Scanner

val input = Scanner(System.`in`)

fun main() {
    startGame()
    //test()
}

fun startGame() {
    println("Hello World!")
    val game = Game()
    val botPlayer = Player.X
    val bot = GameBot(game, Mode.HARD, botPlayer)

    println(
        "Indexes of field(num == index + 1)\n" +
                "|0|1|2|\n" +
                "|3|4|5|\n" +
                "|6|7|8|"
    )
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

fun test() {
    println(indexIntoPosition(20, 4).toString())
    println(positionIntoIndex(2 to 2, 3).toString())
    println(makeEmptyGameField(3).print())
    println(arrayOf(1, 2, 3, 4, 5, 6, 7).contains(arrayOf(7, 6)))
}