import GameData.Companion.indexIntoPosition
import GameData.Companion.playerToCellState
import GameData.Companion.standard_game_field
import GameData.Companion.switchPlayer
import GameData.GameCellState
import GameData.GameState
import GameData.GameCell
import GameData.GameMode
import GameData.Player

class Game(
    private var field: Array<Array<GameCell>> = standard_game_field,
    private var state: GameState = GameState.GAME,
    private var mode: GameMode = GameMode.THREE_TO_THREE,
    private var currentPlayer: Player = Player.X
) {
    init {
        updateGameState()
    }

    fun getField() = field
    fun getState() = state
    fun getMode() = mode
    fun getCurrentPlayer() = currentPlayer

    fun reStart() {
        field = standard_game_field
        state = GameState.GAME
        mode = GameMode.THREE_TO_THREE
        currentPlayer = Player.X
    }

    //TODO
    //TODO scalability,
    // check different by mode

    fun makeTurn(cellIndex: Int): Boolean {
        if (cellIndex in 0 until field.size * field.size) {
            val (row, column) = indexIntoPosition(cellIndex, field.size)
            //Acting if cell is EMPTY and game active
            return if (field[row][column].state == GameCellState.EMPTY && state == GameState.GAME) {
                println("onTurn: cellIndex: $cellIndex, currentPlayer: \"${currentPlayer}\"")
                // Saving move
                field[row][column].state = playerToCellState(currentPlayer)
                // Switching current player
                currentPlayer = switchPlayer(currentPlayer)
                updateGameState()
                true
            } else {
                false
            }
        } else {
            println("Make turn: $cellIndex out of bound ${0 until field.size * field.size}")
            return false
        }
    }

    private fun hasThreeInRow(cellToCheck: GameCellState): Boolean {
        val threeInRow = arrayOf(
            GameCell(cellToCheck),
            GameCell(cellToCheck),
            GameCell(cellToCheck)
        )
        //Checking rows and columns
        for (row in 0..2) {
            if (field[row].contentEquals(threeInRow)) return true
            if (arrayOf(field[0][row], field[1][row], field[2][row]).contentEquals(threeInRow)) return true
        }
        //Checking diagonals
        if (arrayOf(
                field[0][0],
                field[1][1],
                field[2][2]
            ).contentEquals(threeInRow)
        ) return true
        if (arrayOf(
                field[0][2],
                field[1][1],
                field[2][0]
            ).contentEquals(threeInRow)
        ) return true
        //If no three in a row, then return false
        return false
    }

    //TODO
    //TODO
    //TODO

    private fun updateGameState() {
        //Checking X or O win
        val xWinState = hasThreeInRow(GameCellState.CROSS)
        val oWinState = hasThreeInRow(GameCellState.CIRCLE)
        println("X WINS: $xWinState, O WINS: $oWinState")
        //Checking isFull state
        var isFull = true
        for (row in field) {
            if (row.contains(GameCell())) {
                isFull = false
                break
            }
        }
        //Checking draw state
        if (isFull && !oWinState && !xWinState) {
            state = GameState.DRAW
            println("updateGameState: $state")
            return
        }
        //Checking impossible state
        if (oWinState && xWinState) {
            state = GameState.ERROR
            println("updateGameState: $state")
            return
        }
        var countOfX = 0
        var countOfO = 0
        field.forEach { row ->
            row.forEach { cell ->
                if (cell.state == GameCellState.CIRCLE) countOfO++
                if (cell.state == GameCellState.CROSS) countOfX++
            }
        }
        if (maxOf(countOfO, countOfX) - minOf(countOfO, countOfX) >= 2) {
            state = GameState.ERROR
            println("updateGameState: $state")
            return
        }
        //Checking win state
        state = when {
            xWinState -> GameState.X_WINS
            oWinState -> GameState.O_WINS
            else -> state
        }
        println("updateGameState: $state")
    }
}