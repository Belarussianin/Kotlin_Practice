package gameCrossZero.rebranding2022.model

sealed class FieldType(
    val width: Int,
    val height: Int
) {
    class Standard : FieldType(3, 3)
    class Large : FieldType(5, 5)
    class ExtraLarge : FieldType(10, 10)
}