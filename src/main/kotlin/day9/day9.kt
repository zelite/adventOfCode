package day9

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {
    val rope = List(10) { Knot() }
    val head = rope[0]
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day9/input.txt"))
    allLines.map { Instruction(it) }.forEach { moveHeadAndTail(rope, it) }

    println(head.visitedPositions)
    println("Part1: Tail visited ${rope[1].visitedPositions.size} positions")
    println("Part2: Tail visited ${rope[9].visitedPositions.size} positions")
}

fun moveHeadAndTail(rope: List<Knot>, instruction: Instruction) {
    instruction.performInstruction(rope[0]) { moveRope(rope) }
}

fun moveRope(rope: List<Knot>) {
    if (rope.size <= 1) {
        return
    }
    moveKnot(rope[1], rope[0])
    moveRope(rope.drop(1))
}


fun moveKnot(follower: Knot, toFollow: Knot) {
    if (!isAdjacent(follower, toFollow)) {
        moveTailAfterHead(follower, toFollow)
    }
}

fun moveTailAfterHead(tail: Knot, head: Knot) {

    val moves = mutableListOf<() -> Unit>()

    if (tail.currentPosition.first - head.currentPosition.first > 0) {
        moves.add { tail.moveLeft() }
    }
    if (tail.currentPosition.first - head.currentPosition.first < 0) {
        moves.add { tail.moveRight() }
    }
    if (tail.currentPosition.second - head.currentPosition.second > 0) {
        moves.add { tail.moveDown() }
    }
    if (tail.currentPosition.second - head.currentPosition.second < 0) {
        moves.add { tail.moveUp() }
    }
    moves.forEach { it() }
    tail.updateVisited()

}

fun isAdjacent(tail: Knot, head: Knot): Boolean {
    return abs(tail.currentPosition.first - head.currentPosition.first) <= 1 &&
            abs(tail.currentPosition.second - head.currentPosition.second) <= 1
}
