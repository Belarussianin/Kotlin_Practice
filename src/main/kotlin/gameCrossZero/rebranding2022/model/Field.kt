package gameCrossZero.rebranding2022.model

class Field(
    val fieldType: FieldType
) {
    private val field: Array<MutableList<PlayerType?>> =
        Array(fieldType.height) {
            MutableList(fieldType.width) {
                null
            }
        }

    fun isMovePossible(row: Int, column: Int): Boolean {
        return when {
            row in field.indices && column in field[row].indices -> {
                return field[row][column] == null
            }
            else -> false
        }
    }

    fun isMovePossible(index: Int): Boolean {
        return isMovePossible(index / field[0].size, index % field[0].size)
    }

    fun setMove(row: Int, column: Int, playerType: PlayerType): Boolean {
        return if (isMovePossible(row, column)) {
            field[row].add(column, playerType)
            true
        } else false
    }

    fun setMove(index: Int, playerType: PlayerType): Boolean {
        return setMove(index / field[0].size, index % field[0].size, playerType)
    }
}