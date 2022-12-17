package day14

import kotlin.math.sign

class RockPath(listOfPoints: List<String>) {

    private var bottomOfCave: Int? = null
    var path: MutableSet<HasCoordinates>

    init {
        val initialRocks = listOfPoints.map { Rock(it) }
        val interpolatedRocks = mutableSetOf<HasCoordinates>()
        interpolatedRocks.addAll(initialRocks)
        for (i in initialRocks.indices) {
            val firstPoint = initialRocks[i]
            val secondPoint = initialRocks.getOrNull(i + 1)
            if (secondPoint != null) {
                val dx = sign((secondPoint.x - firstPoint.x).toDouble())
                val dy = sign((secondPoint.y - firstPoint.y).toDouble())
                val nextPoint = Rock(firstPoint)
                while (!(nextPoint.x == secondPoint.x && nextPoint.y == secondPoint.y)) {
                    nextPoint.x += dx.toInt()
                    nextPoint.y += dy.toInt()
                    interpolatedRocks.add(Rock(nextPoint))
                }
            }
        }
        path = interpolatedRocks
    }

    fun isDownFree(thing: HasCoordinates): Boolean {
        return !(path.contains(Rock(thing.x, thing.y + 1)) || path.contains(Sand(thing.x, thing.y + 1)))
    }

    fun isDownLeftFree(thing: HasCoordinates): Boolean {
        return !(path.contains(Rock(thing.x - 1, thing.y + 1)) || path.contains(
            Sand(
                thing.x - 1, thing.y + 1
            )
        ))
    }

    fun isDownRightFree(thing: HasCoordinates): Boolean {
        return !(path.contains(Rock(thing.x + 1, thing.y + 1)) || path.contains(
            Sand(
                thing.x + 1, thing.y + 1
            )
        ))
    }

    fun isBottomOfCave(thing: HasCoordinates): Boolean {
        if (bottomOfCave == null) {
            bottomOfCave = path.filterIsInstance<Rock>().maxOf { it.y } + 1
        }
        return thing.y == bottomOfCave
    }

    fun combine(other: RockPath): RockPath {
        val newRockPath = RockPath(emptyList())
        newRockPath.path.addAll(path)
        newRockPath.path.addAll((other.path))
        return newRockPath
    }

    fun add(sand: Sand) {
        path.add(sand)
    }


}

data class Rock(override var x: Int, override var y: Int) : HasCoordinates {
    constructor(pointCoords: String) : this(0, 0) {
        pointCoords.split(",").also {
            x = it[0].trim().toInt()
            y = it[1].trim().toInt()
        }
    }

    constructor(toCopy: Rock) : this(toCopy.x, toCopy.y)
}
