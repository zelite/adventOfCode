package day14

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day14/input.txt"))
    val objectsInWall: RockPath =
        allLines.map { s -> s.split("->") }.map { s -> RockPath(s) }.reduce { a, b -> a.combine(b) }

    println(objectsInWall)
    var amountOfSand = 0

    var sand = Sand()

    while (true) {

        sand.move(objectsInWall)
        if (sand.atRest) {
            objectsInWall.add(sand)
            amountOfSand++
            println("Amount of sand $amountOfSand")
            println("Sand stopped at: $sand")
            if (sand == Sand()) {
                break
            }
            sand = Sand()

        }
    }
    println("How many units of sand come to rest before sand starts flowing into the abyss below? $amountOfSand")

}


