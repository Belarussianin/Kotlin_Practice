import GameData.Companion.gameModeToInt
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
    private val field: Array<Array<GameCell>> = standard_game_field,
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
        resetGameField()
        state = GameState.GAME
        currentPlayer = Player.X
    }

    private fun resetGameField() {
        field.forEach { row ->
            row.forEach { cell ->
                cell.state = GameCellState.EMPTY
            }
        }
    }

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
                println("Cell is not empty or game ended")
                //TODO for console debug purpose, delete later
                println("Enemy turn index: ")
                makeTurn(input.nextInt())
                //TODO
                false
            }
        } else {
            println("Make turn: $cellIndex out of bound ${0 until field.size * field.size}")
            return false
        }
    }

    //TODO
    //TODO scalability,
    // MANY DIAGS CHECK, IF THERE IS MORE THAN 2 DIAGONALS

    private fun hasWinState(cellToCheck: GameCellState): Boolean {
        val rowToCheck = Array(gameModeToInt(mode)) {
            GameCell(cellToCheck)
        }
        //Checking rows and columns
        for (rowIndex in field.indices) {
            //row
            if (field[rowIndex].contains(rowToCheck)) return true
            //column
            if (Array(field.size) { columnIndex ->
                field[columnIndex][rowIndex]
                }.contains(rowToCheck)) {
                return true
            }
        }
        //Checking diagonals
        //Main diag From top to bottom
        if (Array(field.size) { index ->
                field[index][index]
            }.contains(rowToCheck)) {
            return true
        }
        //Second diag from bottom to top
        var secondDiag: Array<GameCell> = emptyArray()
        for (rowIndex in field.indices.reversed()) {
            secondDiag = secondDiag.plusElement(field[rowIndex][field.lastIndex - rowIndex])
        }
        if (secondDiag.contains(rowToCheck)) {
            return true
        }
        //If no three in a row, then return false
        return false
    }

    //TODO
    //TODO
    //TODO

    private fun updateGameState() {
        //Checking X or O win
        val xWinState = hasWinState(GameCellState.CROSS)
        val oWinState = hasWinState(GameCellState.CIRCLE)
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