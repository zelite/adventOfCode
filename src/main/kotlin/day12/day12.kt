package day12

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day12/input.txt"))
    val rowsOfPoints = allLines.map { line -> line.toCharArray().toList() }
        .mapIndexed { rowIndex, row -> row.mapIndexed { colIndex, node -> Node(colIndex, rowIndex, node) } }

    val startPoint = rowsOfPoints.flatten().find { it.elevation == 'S' }.also {
        it!!.elevation = 'a'
    }
    val endPoint = rowsOfPoints.flatten().find { it.elevation == 'E' }.also { it!!.elevation = 'z' }

    val edgesGoingUp: List<Edge> = rowsOfPoints.map { row ->
        row.map { node ->
            generateEdges(node, rowsOfPoints) { a, b -> Edge(a, b, 1) }
        }
    }.flatten().flatten()

    println(
        "What is the fewest steps required to move from your current position " + "to the location that should get the best signal?"
    )
    println(bellmanFord(edgesGoingUp, rowsOfPoints.flatten(), startPoint!!)[endPoint])

    val edgesGoingDown: List<Edge> = rowsOfPoints.map { row ->
        row.map { node ->
            generateEdges(node, rowsOfPoints) { a, b -> Edge(b, a, 1) }
        }
    }.flatten().flatten()

    println(
        "What is the fewest steps required to move starting from any square " + "with elevation a to the location that should get the best signal?"
    )
    println(bellmanFord(edgesGoingDown, rowsOfPoints.flatten(), endPoint!!).entries.filter { it.key.elevation == 'a' }
        .map { it.value }.minOf { it })
}

fun generateEdges(node: Node, rowsOfPoints: List<List<Node>>, edgeBuilder: (Node, Node) -> Edge): List<Edge> {
    var up: Node? = null
    var down: Node? = null
    var left: Node? = null
    var right: Node? = null

    //(0, 0) is top left
    if (node.y > 0) up = rowsOfPoints[node.y - 1][node.x]
    if (node.y < rowsOfPoints.size - 1) down = rowsOfPoints[node.y + 1][node.x]
    if (node.x > 0) left = rowsOfPoints[node.y][node.x - 1]
    if (node.x < rowsOfPoints[0].size - 1) right = rowsOfPoints[node.y][node.x + 1]
    return sequenceOf(up, down, left, right).filterNotNull().map { n ->
        if (abs(node.elevation - n.elevation) <= 1 || n.elevation < node.elevation) edgeBuilder(node, n) else null
    }.filterNotNull().toList()
}

fun bellmanFord(edges: List<Edge>, nodes: List<Node>, startNode: Node): Map<Node, Int> {

    val targetToDistanceFromStart: MutableMap<Node, Int> = nodes.associateWith { Int.MAX_VALUE }.toMutableMap()
    targetToDistanceFromStart[startNode] = 0
    for (n in nodes) {
        for (e in edges) {
            val u = e.source
            val v = e.destination
            if (targetToDistanceFromStart[u] != Int.MAX_VALUE && targetToDistanceFromStart[u]!! + 1 < targetToDistanceFromStart[v]!!) {
                targetToDistanceFromStart[v] = targetToDistanceFromStart[u]!! + 1
            }
        }
    }
    return targetToDistanceFromStart
}
