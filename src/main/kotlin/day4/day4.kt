package day4

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day4/input.txt"))
    val pairsOfIntervals = allLines.map { line -> line.split(",") }
        .map { listOfTwo -> Pair(Interval(listOfTwo[0]), Interval(listOfTwo[1])) }

    val countFullyContain =
        pairsOfIntervals.count { p -> p.first.fullOverlap(p.second) || p.second.fullOverlap(p.first) }
    val countOverlaps = pairsOfIntervals.count { p -> p.first.overlap(p.second) }

    println("There are $countFullyContain pairs where one range fully contains the other")
    println("There are $countOverlaps pairs where one range overlaps the other")
}

