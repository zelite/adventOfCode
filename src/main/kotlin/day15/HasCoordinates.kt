package day15

import kotlin.math.abs

interface HasCoordinates {
    val x: Int
    val y: Int

    fun manhattanDistance(other: HasCoordinates): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}