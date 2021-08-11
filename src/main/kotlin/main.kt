import GameBotData.Mode
import GameData.Player
import java.util.Scanner

val input = Scanner(System.`in`)

fun main() {
    println("Hello World!")
    val game = Game()
    val botPlayer = Player.X
    val bot = GameBot(game, Mode.HARD, botPlayer)

    println("Indexes of field(num == -1)\n" +
            "|0|1|2|\n" +
            "|3|4|5|\n" +
            "|6|7|8|")
    when(botPlayer) {
        Player.X -> {
            while (game.getState() == GameData.GameState.GAME) {
                val botMove = bot.getIndexToMoveOrNull() ?: break
                println("\nBot move number: ${botMove + 1}")
                bot.makeMove()
                if (game.getState() != GameData.GameState.GAME) break
                println("Enemy number: ")
                game.makeTurn(input.nextInt() - 1)
            }
        }
        Player.O -> {
            while (game.getState() == GameData.GameState.GAME) {
                println("Enemy number: ")
                game.makeTurn(input.nextInt() - 1)
                val botMove = bot.getIndexToMoveOrNull() ?: break
                println("\nBot move number: ${botMove + 1}")
                bot.makeMove()
            }
        }
    }
}