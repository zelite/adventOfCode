package day6

import java.nio.file.Files
import java.nio.file.Path

private const val WINDOW_SIZE_PACKET_MARKER = 4
private const val WINDOW_SIZE_MESSAGE_MARKER = 14

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day6/input.txt"))
    val firstUniqueFourCharsIndex =
        allLines[0].windowed(WINDOW_SIZE_PACKET_MARKER, 1).indexOfFirst { s -> s.toSet().size == s.length }
    val firstUniqueFourteenCharsIndex =
        allLines[0].windowed(WINDOW_SIZE_MESSAGE_MARKER, 1).indexOfFirst { s -> s.toSet().size == s.length }

    println("start-of-packet marker at index: ${firstUniqueFourCharsIndex + WINDOW_SIZE_PACKET_MARKER}")
    println("start-of-packet marker at index: ${firstUniqueFourteenCharsIndex + WINDOW_SIZE_MESSAGE_MARKER}")

}