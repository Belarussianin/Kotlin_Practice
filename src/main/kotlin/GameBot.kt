import GameBotData.Mode
import GameData.Companion.cellsPositionToIndex
import GameData.Companion.playerToCellState
import GameData.Companion.switchPlayer
import GameData.GameCellState
import GameData.GameCell
import GameData.Player

class GameBot(
    private val game: Game,
    private var mode: Mode,
    private var botPlayer: Player
) {
    fun makeMove(): Boolean {
        if (game.getCurrentPlayer() == botPlayer && game.getState() == GameData.GameState.GAME) {
            game.makeTurn(getIndexToMoveOrNull() ?: -1)
            val field = game.getField()
            println(
                "\ncurrent field:\n${field[0].joinToString(", ", "[", "]")}\n" +
                        field[1].joinToString(", ", "[", "]") + "\n" +
                        field[2].joinToString(", ", "[", "]")
            )
            return true
        }
        return false
    }

    fun getIndexToMoveOrNull(): Int? {
        val field = game.getField()
        val state = game.getState()
        var indexToMove: Int? = null
        //Checking all conditions only if game is play
        if (state == GameData.GameState.GAME) {
            when(mode) {
                Mode.EASY -> {
                    //Move to closest empty cell
                    indexToMove = closestEmptyMoveIndexOrNull()
                }
                Mode.MEDIUM -> {
                    //Move to closest empty cell
                    indexToMove = closestEmptyMoveIndexOrNull()
                    //Check player win
                    indexToMove = getIndexToWinOrNull(switchPlayer(botPlayer)) ?: indexToMove
                }
                Mode.HARD -> {
                    //Check empty cells
                    if (closestEmptyMoveIndexOrNull() != null) {
                        //Move to closest empty cell
                        indexToMove = closestEmptyMoveIndexOrNull() ?: indexToMove
                        //Move to closest empty corner if player have center
                        indexToMove = getClosestEmptyCornerOrNull() ?: indexToMove
                        //Check player move to center
                        indexToMove = getIndexToPreventPlayerWinOrNull() ?: indexToMove
                        //Check player win
                        indexToMove = getIndexToWinOrNull(switchPlayer(botPlayer)) ?: indexToMove
                        //Check center
                        if (field[1][1].state == GameCellState.EMPTY) indexToMove = cellsPositionToIndex[1 to 1]
                        //Check bot win
                        indexToMove = getIndexToWinOrNull(botPlayer) ?: indexToMove
                    }
                }
            }
        }
        return indexToMove
    }

    private fun closestEmptyMoveIndexOrNull(): Int? {
        val field = game.getField()
        for (row in field.indices) {
            for (column in field[row].indices) {
                if (field[row][column].state == GameCellState.EMPTY) return cellsPositionToIndex[row to column]
            }
        }
        return null //If no empty cells
    }

    private fun getClosestEmptyCornerOrNull(): Int? {
        val field = game.getField()
        if (field[0][0].state == GameCellState.EMPTY) return cellsPositionToIndex[0 to 0]
        if (field[0][2].state == GameCellState.EMPTY) return cellsPositionToIndex[0 to 2]
        if (field[2][0].state == GameCellState.EMPTY) return cellsPositionToIndex[2 to 0]
        if (field[2][2].state == GameCellState.EMPTY) return cellsPositionToIndex[2 to 2]
        return null
    }

    private fun getIndexToPreventPlayerWinOrNull(): Int? {
        val field = game.getField()
        val playerCellState: GameCellState = playerToCellState(switchPlayer(botPlayer))
        //Check 8 combinations of player moves after center
        if (field[1][1].state == playerToCellState(botPlayer)) {
            if (field[0][0].state == playerCellState && field[2][1].state == playerCellState) {
                if (field[1][0].state == GameCellState.EMPTY) {
                    return cellsPositionToIndex[1 to 0]
                }
            }
            if (field[0][0].state == playerCellState && field[1][2].state == playerCellState) {
                if (field[0][1].state == GameCellState.EMPTY) {
                    return cellsPositionToIndex[0 to 1]
                }
            }
            if (field[2][0].state == playerCellState && field[1][2].state == playerCellState) {
                if (field[2][1].state == GameCellState.EMPTY) {
                    return cellsPositionToIndex[2 to 1]
                }
            }
            if (field[0][1].state == playerCellState && field[2][2].state == playerCellState) {
                if (field[1][2].state == GameCellState.EMPTY) {
                    return cellsPositionToIndex[1 to 2]
                }
            }
        }
        return null
    }

    private fun getIndexToWinOrNull(playerToCheck: Player): Int? {
        val field = game.getField()
        val cellStateToCheck: GameCellState = playerToCellState(playerToCheck)
        val firstWInRow = arrayOf(
            GameCell(GameCellState.EMPTY),
            GameCell(cellStateToCheck),
            GameCell(cellStateToCheck)
        )
        val secondWInRow = arrayOf(
            GameCell(cellStateToCheck),
            GameCell(GameCellState.EMPTY),
            GameCell(cellStateToCheck)
        )
        val thirdWInRow = arrayOf(
            GameCell(cellStateToCheck),
            GameCell(cellStateToCheck),
            GameCell(GameCellState.EMPTY)
        )
        //Checking rows and columns
        for (row in 0..2) {
            //Check row
            if (field[row].contentEquals(firstWInRow)) return cellsPositionToIndex[row to 0]
            if (field[row].contentEquals(secondWInRow)) return cellsPositionToIndex[row to 1]
            if (field[row].contentEquals(thirdWInRow)) return cellsPositionToIndex[row to 2]
            //Check column
            if (arrayOf(field[0][row], field[1][row], field[2][row]).contentEquals(firstWInRow)) return cellsPositionToIndex[0 to row]
            if (arrayOf(field[0][row], field[1][row], field[2][row]).contentEquals(secondWInRow)) return cellsPositionToIndex[1 to row]
            if (arrayOf(field[0][row], field[1][row], field[2][row]).contentEquals(thirdWInRow)) return cellsPositionToIndex[2 to row]
        }
        //Checking diagonals
        //first
        if (arrayOf(field[0][0], field[1][1], field[2][2]).contentEquals(firstWInRow)) return cellsPositionToIndex[0 to 0]
        if (arrayOf(field[0][0], field[1][1], field[2][2]).contentEquals(secondWInRow)) return cellsPositionToIndex[1 to 1]
        if (arrayOf(field[0][0], field[1][1], field[2][2]).contentEquals(thirdWInRow)) return cellsPositionToIndex[2 to 2]
        //second
        if (arrayOf(field[0][2], field[1][1], field[2][0]).contentEquals(firstWInRow)) return cellsPositionToIndex[0 to 2]
        if (arrayOf(field[0][2], field[1][1], field[2][0]).contentEquals(secondWInRow)) return cellsPositionToIndex[1 to 1]
        if (arrayOf(field[0][2], field[1][1], field[2][0]).contentEquals(thirdWInRow)) return cellsPositionToIndex[2 to 0]
        //If no three in a row, then return -1
        return null
    }
}