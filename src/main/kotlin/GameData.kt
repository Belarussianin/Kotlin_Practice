sealed class GameData {
    enum class Player {
        O, X
    }

    enum class GameCellState {
        EMPTY, CIRCLE, CROSS
    }

    enum class GameState {
        GAME, X_WINS, O_WINS, DRAW, ERROR
    }

    data class GameCell(
        var state: GameCellState = GameCellState.EMPTY
    )

    companion object {
        fun playerToCellState(player: Player): GameCellState =
            if (player == Player.X) GameCellState.CROSS else GameCellState.CIRCLE

        fun switchPlayer(player: Player): Player = if (player == Player.X) Player.O else Player.X

        val cellsIndexToPosition: Map<Int, Pair<Int, Int>>
            get() = mapOf(
                0 to Pair(0, 0),
                1 to Pair(0, 1),
                2 to Pair(0, 2),
                3 to Pair(1, 0),
                4 to Pair(1, 1),
                5 to Pair(1, 2),
                6 to Pair(2, 0),
                7 to Pair(2, 1),
                8 to Pair(2, 2)
            )

        val cellsPositionToIndex: Map<Pair<Int, Int>, Int>
            get() = mapOf(
                Pair(0, 0) to 0,
                Pair(0, 1) to 1,
                Pair(0, 2) to 2,
                Pair(1, 0) to 3,
                Pair(1, 1) to 4,
                Pair(1, 2) to 5,
                Pair(2, 0) to 6,
                Pair(2, 1) to 7,
                Pair(2, 2) to 8
            )

        val standard_game_state get() = GameState.GAME
        val standard_current_player get() = Player.X
        val standard_game_field
            get() = arrayOf(
                arrayOf(GameCell(), GameCell(), GameCell()),
                arrayOf(GameCell(), GameCell(), GameCell()),
                arrayOf(GameCell(), GameCell(), GameCell()),
            )
    }
}