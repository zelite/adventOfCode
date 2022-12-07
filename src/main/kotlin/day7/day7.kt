package day7

import java.nio.file.Files
import java.nio.file.Path

const val TOTAL_SIZE_OF_DISK = 70_000_000
const val SIZE_NEEDED_FOR_UPDATE = 30_000_000
fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day7/input.txt"))
    val indexesStartOfCommands =
        allLines.mapIndexed { index, s -> if (isStartOfCommand(s)) index else -1 }
            .filter { i -> i != -1 }

    val commandStrings = mutableListOf<List<String>>()

    for (i in indexesStartOfCommands.indices) {
        val start = indexesStartOfCommands[i]
        val end =
            if (i + 1 == indexesStartOfCommands.size) allLines.size else indexesStartOfCommands[i + 1]
        commandStrings.add(allLines.slice(start until end))
    }

    val fs = FileSystem()
    commandStrings.map { buildCommand(it) }.forEach {
            println(it.describeCommand())
            it.applyCommand(fs)
        }

    val allDirsSizes = fs.getAllDirs().map { it.size }
    val sumOfSizes = allDirsSizes.filter { s -> s <= 100_000 }.sum()


    println("Sum of sizes of directories with size at most 100000: $sumOfSizes")

    val totalUsedSpace = fs.root.size
    val currentFreeSpace = TOTAL_SIZE_OF_DISK - totalUsedSpace

    val sizeDirToDelete =
        allDirsSizes.sorted().find { s -> currentFreeSpace + s >= SIZE_NEEDED_FOR_UPDATE }

    println("Size of smallest directory to delete: $sizeDirToDelete ")


}

fun isStartOfCommand(s: String): Boolean = s.startsWith("$")

fun buildCommand(commandStrings: List<String>): FileSystemCommand {
    if (commandStrings[0].startsWith(("$ cd"))) {
        return CdCommand(commandStrings)
    }
    return LsCommand(commandStrings)
}