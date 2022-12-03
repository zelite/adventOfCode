package day3

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day3/input.txt"))
    val backpacks = allLines.map { l -> Backpack(l) }
    val sumOfCommonItemsPriority =
        backpacks.map { b -> b.getCommonItemBetweenCompartments() }.sumOf { item -> itemPriority(item) }

    val sumGroupsOfThreeItemsPriority = backpacks.chunked(3).map { listOfThree ->
        listOfThree.reduce { a, b -> a.getBackpackWithCommonItems(b) }
    }.map { b -> b.getContent() }.sumOf { content -> itemPriority(content[0]) }

    println("Part 1")
    println("The sum of priorities is in common items of single backpacks is$sumOfCommonItemsPriority")
    println("Part 2")
    println("The sum of priorities in the 3 elf groups badges is $sumGroupsOfThreeItemsPriority")

}

fun itemPriority(item: Char): Int {
    return if (item.isLowerCase()) {
        item.code.mod('a'.code) + 1
    } else {
        item.code.mod('A'.code) + 1 + itemPriority('z')
    }
}