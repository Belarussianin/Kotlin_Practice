fun Array<Array<GameData.GameCell>>.print() {
    println("Current field: ")
    for (row in this) println(row.joinToString(", ", "[", "]"))
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