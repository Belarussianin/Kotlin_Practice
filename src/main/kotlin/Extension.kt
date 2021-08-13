fun Array<Array<GameData.GameCell>>.print() {
    val ANSI_GREEN = "\u001B[32m"
    val ANSI_RED = "\u001B[31m"
    val ANSI_RESET = "\u001B[0m"
    println("Current field: ")
    this.forEach { row ->
        row.forEach { cell ->
            when(cell.state) {
                GameData.GameCellState.CROSS -> {
                    print(ANSI_RED + "${cell.state} " + ANSI_RESET)
                }
                GameData.GameCellState.CIRCLE -> {
                    print(ANSI_GREEN + "${cell.state} " + ANSI_RESET)
                }
                else -> {
                    print("${cell.state} ")
                }
            }
        }
        println()
    }
}

fun Array<*>.contains(other: Array<*>): Boolean {
    var contains = true
    for (arrIndex in 0..(this.lastIndex - other.lastIndex)) {
        for (arrIndex2 in other.indices) {
            if (this[arrIndex + arrIndex2] != other[arrIndex2]) {
                contains = false
            }
        }
        if (contains) return true else contains = true
    }
    return false
}

fun Array<*>.containsStartAt(other: Array<*>): Int? {
    var contains = true
    for (arrIndex in 0..(this.lastIndex - other.lastIndex)) {
        for (arrIndex2 in other.indices) {
            if (this[arrIndex + arrIndex2] != other[arrIndex2]) {
                contains = false
            }
        }
        if (contains) {
            return arrIndex
        } else contains = true
    }
    return null
}