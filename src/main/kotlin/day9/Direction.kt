package day9

enum class Direction(val s: String) {
    LEFT("L"),
    RIGHT("R"),
    UP("U"),
    DOWN("D");

    companion object {
        private val map = Direction.values().associateBy { it.s }
        infix fun from(value: String) = map[value]
    }
}