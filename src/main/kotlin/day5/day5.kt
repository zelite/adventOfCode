package day5

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day5/input.txt"))
    val emptyLineIndex = allLines.indexOfFirst { s -> s.isBlank() }
    val cratesLines = allLines.subList(fromIndex = 0, toIndex = emptyLineIndex - 1)
    val crates = Crates(cratesLines)
    allLines.subList(emptyLineIndex + 1, allLines.size)
        .map { s -> s.split("move", "from", "to").filter { it.isNotBlank() } }.map { s -> s.joinToString("") }
        .map { s -> Instruction(s) }.forEach { i -> i.executeInstruction(crates) }

    println("Top Letters ${crates.getTopLetters()}")


}