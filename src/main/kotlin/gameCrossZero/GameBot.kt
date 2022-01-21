package gameCrossZero

import gameCrossZero.GameBotData.Mode
import gameCrossZero.GameData.Companion.gameModeToInt
import gameCrossZero.GameData.Companion.playerToCellState
import gameCrossZero.GameData.Companion.positionIntoIndex
import gameCrossZero.GameData.Companion.switchPlayer
import gameCrossZero.GameData.GameCellState
import gameCrossZero.GameData.GameCell
import gameCrossZero.GameData.GameMode
import gameCrossZero.GameData.Player

class GameBot(
    private val game: Game,
    private var mode: Mode,
    private var botPlayer: Player
) {
    fun makeMove(): Boolean {
        if (game.getCurrentPlayer() == botPlayer && game.getState() == GameData.GameState.GAME) {
            game.makeTurn(getIndexToMoveOrNull() ?: -1)
            game.getField().print()
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
            when (mode) {
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
                        if (game.getMode() == GameMode.THREE_TO_THREE) {
                            if (field[1][1].state == GameCellState.EMPTY) indexToMove =
                                positionIntoIndex(1 to 1, field.size)
                        }
                        //Check bot win
                        indexToMove = getIndexToWinOrNull(botPlayer) ?: indexToMove
                    }
                }
            }
        }
        return indexToMove
    }

    //TODO
    //TODO scalability
    //TODO

    private fun closestEmptyMoveIndexOrNull(): Int? {
        val field = game.getField()
        for (row in field.indices) {
            for (column in field[row].indices) {
                if (field[row][column].state == GameCellState.EMPTY) return positionIntoIndex(row to column, field.size)
            }
        }
        return null //If no empty cells
    }

    private fun getClosestEmptyCornerOrNull(): Int? {
        val field = game.getField()
        if (field[0][0].state == GameCellState.EMPTY) return positionIntoIndex(0 to 0, field.size)
        if (field[0][2].state == GameCellState.EMPTY) return positionIntoIndex(0 to 2, field.size)
        if (field[2][0].state == GameCellState.EMPTY) return positionIntoIndex(2 to 0, field.size)
        if (field[2][2].state == GameCellState.EMPTY) return positionIntoIndex(2 to 2, field.size)
        return null
    }

    private fun getIndexToPreventPlayerWinOrNull(): Int? {
        val field = game.getField()
        val playerCellState: GameCellState = playerToCellState(switchPlayer(botPlayer))
        //Check 8 combinations of player moves after center
        if (field[1][1].state == playerToCellState(botPlayer)) {
            if (field[0][0].state == playerCellState && field[2][1].state == playerCellState) {
                if (field[1][0].state == GameCellState.EMPTY) {
                    return positionIntoIndex(1 to 0, field.size)
                }
            }
            if (field[0][0].state == playerCellState && field[1][2].state == playerCellState) {
                if (field[0][1].state == GameCellState.EMPTY) {
                    return positionIntoIndex(0 to 1, field.size)
                }
            }
            if (field[2][0].state == playerCellState && field[1][2].state == playerCellState) {
                if (field[2][1].state == GameCellState.EMPTY) {
                    return positionIntoIndex(2 to 1, field.size)
                }
            }
            if (field[0][1].state == playerCellState && field[2][2].state == playerCellState) {
                if (field[1][2].state == GameCellState.EMPTY) {
                    return positionIntoIndex(1 to 2, field.size)
                }
            }
        }
        return null
    }

    //TODO
    //TODO
    //TODO

    private fun generateWinRows(checkSize: Int, cellStateToCheck: GameCellState): Array<Array<GameCell>> {
        var winRows: Array<Array<GameCell>> = arrayOf()
        for (i in 0 until checkSize) {
            winRows = winRows.plusElement(
                Array(checkSize) { index ->
                    if (index == i) {
                        GameCell(GameCellState.EMPTY)
                    } else {
                        GameCell(cellStateToCheck)
                    }
                }
            )
        }
        return winRows
    }

    //TODO
    //TODO scalability,
    // MANY DIAGS CHECK, IF THERE IS MORE THAN 2 DIAGONALS

    private fun getIndexToWinOrNull(playerToCheck: Player): Int? {
        val field = game.getField()
        val cellStateToCheck: GameCellState = playerToCellState(playerToCheck)
        //GameCrossZero.Game have 2 modes to check 3x3 and 5x5
        val winRows = generateWinRows(gameModeToInt(game.getMode()), cellStateToCheck)
        //Checking rows and columns
        for (rowIndex in field.indices) {
            for (winRowIndex in winRows.indices) {
                //Check row
                if (field[rowIndex].contains(winRows[winRowIndex])) {
                    return positionIntoIndex(rowIndex to winRowIndex, field.size)
                }
                //Check column
                if (Array(field.size) { columnIndex ->
                        field[columnIndex][rowIndex]
                    }.contains(winRows[winRowIndex])) {
                    return positionIntoIndex(winRowIndex to rowIndex, field.size)
                }
            }
        }
        //Checking diagonals
        //Main diag From top to bottom
        val firstDiag: Array<GameCell> = Array(field.size) { index ->
            field[index][index]
        }
        //From bottom to top
        var secondDiag: Array<GameCell> = emptyArray()
        for (rowIndex in field.indices.reversed()) {
            secondDiag = secondDiag.plusElement(field[rowIndex][field.lastIndex - rowIndex])
        }
        for (winRowIndex in winRows.indices) {
            //first
            if (firstDiag.contains(winRows[winRowIndex])) {
                val winPosition = firstDiag.containsStartAt(winRows[winRowIndex])!! + winRowIndex
                return positionIntoIndex(winPosition to winPosition, field.size)
            }
            //second
            if (secondDiag.contains(winRows[winRowIndex])) {
                val winPosition = secondDiag.containsStartAt(winRows[winRowIndex])!! + winRowIndex
                return positionIntoIndex(winPosition to winPosition, field.size)
            }
        }
        return null
    }
}