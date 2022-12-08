package day8

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day8/input.txt"))
    val listOfTrees = allLines.mapIndexed { i, l ->
        l.toCharArray().map { c -> c.digitToInt() }.mapIndexed { j, c -> Tree(c, j, i) }.toList()
    }

    listOfTrees.flatten().map { t -> t.visible = isVisibleFromEdge(t, listOfTrees) }

    val visibleTrees = listOfTrees.flatten().count { t -> t.visible }

    println("Visible trees: $visibleTrees")

    listOfTrees.flatten().map { t -> t.scenicScore = calculateScenicScore(t, listOfTrees) }

    val highestScenicScore = listOfTrees.flatten().maxOf { t -> t.scenicScore }

    println("Max scenic score: $highestScenicScore")

}

fun calculateScenicScore(t: Tree, allTrees: List<List<Tree>>): Int {
    return listOf(
        lookDown(t, allTrees),
        lookUp(t, allTrees),
        lookRight(t, allTrees),
        lookLeft(t, allTrees)
    )
        .map { l -> calculateViewDistance(t, l) }
        .reduce { a, b -> a * b }
}

fun lookUp(t: Tree, allTrees: List<List<Tree>>): List<Tree> {
    val y = 0 until t.posY
    return y.reversed().map { allTrees[it][t.posX] }
}

fun lookDown(t: Tree, allTrees: List<List<Tree>>): List<Tree> {
    val y = t.posY + 1 until allTrees.size
    return y.map { allTrees[it][t.posX] }
}

fun lookRight(t: Tree, allTrees: List<List<Tree>>): List<Tree> {
    val x = t.posX + 1 until allTrees[t.posY].size
    return x.map { allTrees[t.posY][it] }
}

fun lookLeft(t: Tree, allTrees: List<List<Tree>>): List<Tree> {
    val x = 0 until t.posX
    return x.reversed().map { allTrees[t.posY][it] }
}

fun isVisibleFromEdge(t: Tree, allTrees: List<List<Tree>>): Boolean {
    return isAtEdge(t, allTrees) ||
            listOf(
                lookDown(t, allTrees),
                lookUp(t, allTrees),
                lookRight(t, allTrees),
                lookLeft(t, allTrees)
            )
                .map { treeList -> treeList.takeWhile { it.size < t.size } }
                .mapNotNull { it.lastOrNull() }
                .any { isAtEdge(it, allTrees) }
}

fun isAtEdge(t: Tree, allTrees: List<List<Tree>>): Boolean {
    return t.posX == 0 || t.posY == 0 || t.posX == allTrees[t.posX].size - 1 || t.posY == allTrees.size - 1
}

fun calculateViewDistance(t: Tree, treesInDirection: List<Tree>): Int {

    val indexOfBlockingTree = treesInDirection.indexOfFirst { it.size >= t.size }
    if (indexOfBlockingTree == -1) {
        return treesInDirection.size
    }
    return treesInDirection.subList(0, indexOfBlockingTree + 1).size
}

