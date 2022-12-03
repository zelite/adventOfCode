package day2.part1

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day2/input.txt"))
    val totalScore =
        allLines.map { s -> s.split(" ") }.map { l -> Pair(translateElfMoves(l[0]), translatePlayerMoves(l[1])) }
            .sumOf { p ->
                scoreForResult(
                    determineWinner(
                        p.first, p.second
                    )
                ) + scoreForPlayerMove(p.second)
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

fun translatePlayerMoves(move: String): String {
    return when (move) {
        "X" -> "Rock"
        "Y" -> "Paper"
        else -> "Scissors"
    }
}

fun scoreForPlayerMove(move: String): Int {
    return when (move) {
        "Rock" -> 1
        "Paper" -> 2
        else -> 3
    }
}


fun determineWinner(elfMove: String, playerMove: String): String? {
    val resultsMap = mapOf(
        Pair(
            "Rock", mapOf(
                Pair("Rock", "Draw"), Pair("Paper", "Player"), Pair("Scissors", "Elf")
            )
        ), Pair(
            "Paper", mapOf(
                Pair("Rock", "Elf"), Pair("Paper", "Draw"), Pair("Scissors", "Player")
            )
        ), Pair(
            "Scissors", mapOf(
                Pair("Rock", "Player"), Pair("Paper", "Elf"), Pair("Scissors", "Draw")
            )
        )
    )
    return resultsMap[elfMove]?.get(playerMove)
}