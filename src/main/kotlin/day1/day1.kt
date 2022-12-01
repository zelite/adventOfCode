package day1

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day1/input.txt"))
    val top3 = mutableListOf(0, 0, 0)
    var currentSum = 0;
    for (line in allLines) {
        if (line.isNotBlank()) {
            currentSum += Integer.parseInt(line)
        } else {
            if (isLargerThanTop3(top3, currentSum)) {
                replaceMin(top3, currentSum)
            }
            currentSum = 0;
        }
    }
    if (isLargerThanTop3(top3, currentSum)) {
        replaceMin(top3, currentSum)
    }
    println(top3.sum())
}

fun replaceMin(top3: MutableList<Int>, currentSum: Int) {
    val i = top3.indexOf(top3.minOrNull())
    top3[i] = currentSum
}

fun isLargerThanTop3(top3: List<Int>, newNumber: Int): Boolean {
    return top3.any { i -> newNumber > i }
}