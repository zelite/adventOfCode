package day15

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {
    val pattern = """.*x=(-?\d+), y=(-?\d+):.*x=(-?\d+), y=(-?\d+)""".toRegex()
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day15/input.txt"))

    val sensorBeaconPairs = allLines.map { pattern.matchEntire(it)!!.groupValues }
        .map { Pair(Sensor(it[1].toInt(), it[2].toInt()), Beacon(it[3].toInt(), it[4].toInt())) }
    val emptySpotsInRow = sensorBeaconPairs.map {
        emptySpots(it.first, it.second, 2000000)
    }.reduce { a, b -> a + b }

    println("In the row where y=2000000, how many positions cannot contain a beacon? ${emptySpotsInRow.size}")

}


fun emptySpots(sensor: Sensor, closestBeacon: Beacon, yCoord: Int): Set<EmptySpot> {
    //println("Processing sensor: $sensor")
    val distance = sensor.manhattanDistance(closestBeacon)
    val emptySpots = mutableSetOf<EmptySpot>()
    val dy = sensor.y - yCoord
    if (abs(dy) > distance) {
        return emptySpots
    }
    for (x in -distance..distance) {
        val newSpot = EmptySpot(sensor.x + (x - dy), yCoord)
        if (newSpot.manhattanDistance(sensor) <= distance) {
            emptySpots.add(newSpot)
        }
    }

    emptySpots.remove(EmptySpot(sensor.x, sensor.y))
    emptySpots.remove(EmptySpot(closestBeacon.x, closestBeacon.y))
    return emptySpots
}

