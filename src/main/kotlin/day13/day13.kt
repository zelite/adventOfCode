package day13

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.max

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day13/input.txt"))
    val allListsParsed = allLines.filter { it.isNotBlank() }
        .filterNotNull()
        .map { parseToList(it) }.toMutableList()


    val sumOfIndexesCorrectOrder = allListsParsed.asSequence().chunked(2)
        .map { areInCorrectOrder(it[0], it[1]) }
        .mapIndexed { index, result -> if (result == 1 || result == 0) index + 1 else null }
        .filterNotNull()
        .sum()

    println("What is the sum of the indices of those pairs? $sumOfIndexesCorrectOrder")

    val dividerPacket2 = parseToList("[[2]]")
    val dividerPacket6 = parseToList("[[6]]")
    allListsParsed.add(dividerPacket2)
    allListsParsed.add(dividerPacket6)

    val sortedPackets = allListsParsed.sortedWith { a, b ->
        areInCorrectOrder(a, b)
    }

    sortedPackets.forEach { println(it) }
    val productOfPacketDividerIndexes =
        (sortedPackets.indexOf(dividerPacket2) + 1) * (sortedPackets.indexOf(dividerPacket6) + 1)

    println("What is the decoder key for the distress signal? $productOfPacketDividerIndexes")


}

fun areInCorrectOrder(listA: List<*>, listB: List<*>): Int {
    for (i in 0 until max(
        listA.size,
        listB.size
    )) {
        val result = compareElements(listA.getOrNull(i), listB.getOrNull(i))
        if (result != 0) {
            return result
        }
    }
    return 0
}

fun compareElements(left: Any?, right: Any?): Int {
    if (left == null && right == null) {
        return -1
    }
    if (left == null) {
        return -1
    }
    if (right == null) {
        return 1
    }
    if (left is Int && right is Int) {
        return if (left == right) 0 else if (left < right) -1 else 1
    }

    if (left is List<*> && right is List<*>) {
        return areInCorrectOrder(left, right)
    }
    if (left is List<*> && right is Int) {
        return areInCorrectOrder(left, listOf(right))
    }
    if (left is Int && right is List<*>) {
        return areInCorrectOrder(listOf(left), right)
    }
    throw IllegalArgumentException("can't compare things")
}

fun parseToList(listAsString: String): List<Any> {
    val arrayOfElements =
        listAsString.replace("[", "[,").replace("]", ",]").split(",").filterNot { it.isBlank() }.drop(1).dropLast(1)
    val resultList = mutableListOf<Any>()
    var currentList = resultList
    val deque = ArrayDeque<MutableList<Any>>()
    for (e in arrayOfElements) {
        when (e) {
            "[" -> {
                val newList = mutableListOf<Any>()
                currentList.add(newList)
                deque.addLast(currentList)
                currentList = newList
            }

            "]" -> {
                currentList = deque.removeLast()
            }

            else -> {
                currentList.add(e.toInt())
            }
        }
    }
    return resultList
}

