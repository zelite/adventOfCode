package day9

class Knot {
    var currentPosition = Pair(0, 0)
    val visitedPositions = mutableSetOf(currentPosition)

    fun move(d: Direction) {
        when (d) {
            Direction.UP -> moveUp()
            Direction.DOWN -> moveDown()
            Direction.LEFT -> moveLeft()
            Direction.RIGHT -> moveRight()
        }
        updateVisited()
    }

    fun updateVisited() {
        visitedPositions.add(currentPosition)
    }


    fun moveRight() {
        currentPosition = Pair(currentPosition.first + 1, currentPosition.second)
    }

    fun moveLeft() {
        currentPosition = Pair(currentPosition.first - 1, currentPosition.second)
    }

    fun moveDown() {
        currentPosition = Pair(currentPosition.first, currentPosition.second - 1)
    }

    fun moveUp() {
        currentPosition = Pair(currentPosition.first, currentPosition.second + 1)
    }
}
