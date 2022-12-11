package day11

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day11/input.txt"))
    val monkeys = allLines.filter { it.isNotBlank() }.chunked(6).map { Monkey(it) }.toList()

    repeat(20) {
        monkeys.forEachIndexed { i, monkey ->
            while (true) {
                val targetAndItemPair = monkey.inspectItem() ?: break
                //println("throwing item ${targetAndItemPair.second} from monkey $i to ${targetAndItemPair.first}")
                monkeys[targetAndItemPair.first].giveItem(targetAndItemPair.second)

            }
        }
    }
    val monkeyBusiness = monkeys.sortedBy { it.inspectionCount }.reversed().take(2)
        .map { it.inspectionCount }
        .reduce { a, b -> a * b }

    println(monkeys.map { it.inspectionCount })
    println("The level of monkey business after 20 rounds: $monkeyBusiness")
}