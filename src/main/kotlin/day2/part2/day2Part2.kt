package day2.part2

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day2/input.txt"))
    val totalScore =
        allLines.map { s -> s.split(" ") }.map { l -> Pair(translateElfMoves(l[0]), translateResult(l[1])) }
            .sumOf { p ->
                scoreForPlayerMove(determinePlayerMove(p.first, p.second)) + scoreForResult(p.second)
            }
    println("Total Score: $totalScore")
}

fun scoreForResult(winner: String?): Int {
    return when (winner) {
        "Elf" -> 0
        "Draw" -> 3
        else -> 6
    }
}

fun translateElfMoves(move: String): String {
    return when (move) {
        "A" -> "Rock"
        "B" -> "Paper"
        else -> "Scissors"
    }
}

fun translateResult(move: String): String {
    return when (move) {
        "X" -> "Elf"
        "Y" -> "Draw"
        else -> "Player"
    }
}

fun scoreForPlayerMove(move: String?): Int {
    return when (move) {
        "Rock" -> 1
        "Paper" -> 2
        else -> 3
    }
}


fun determinePlayerMove(elfMove: String, playerMove: String): String? {
    val resultsMap = mapOf(
        Pair(
            "Rock", mapOf(
                Pair("Draw", "Rock"), Pair("Player", "Paper"), Pair("Elf", "Scissors")
            )
        ), Pair(
            "Paper", mapOf(
                Pair("Elf", "Rock"), Pair("Draw", "Paper"), Pair("Player", "Scissors")
            )
        ), Pair(
            "Scissors", mapOf(
                Pair("Player", "Rock"), Pair("Elf", "Paper"), Pair("Draw", "Scissors")
            )
        )
    )
    return resultsMap[elfMove]?.get(playerMove)
}