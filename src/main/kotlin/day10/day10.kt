package day10

import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val allLines = Files.readAllLines(Path.of("src/main/kotlin/day10/input.txt"))
    val cpu = Cpu()
    allLines.forEach { line ->
        val splitLine = line.split(" ")
        when (splitLine[0]) {
            "noop" -> cpu.performNoop()
            "addx" -> cpu.performAddX(splitLine[1].toInt())
        }
    }
    //20th, 60th, 100th, 140th, 180th, and 220th
    println(
        "Sum of signals : " + (
                cpu.getValueAtTimeX(19) * 20 +
                        cpu.getValueAtTimeX(59) * 60 +
                        cpu.getValueAtTimeX(99) * 100 +
                        cpu.getValueAtTimeX(139) * 140 +
                        cpu.getValueAtTimeX(179) * 180 +
                        cpu.getValueAtTimeX(219) * 220)
    )

    val crt = mutableListOf<String>()
    cpu.cycle.forEachIndexed { index, registerValue ->
        val spritePosition = index.mod(40)
        if ((registerValue - 1..registerValue + 1).contains(spritePosition)) {
            crt.add("#")
        } else {
            crt.add(".")
        }
    }
    crt.windowed(40, step = 40).map { l -> l.joinToString("") }
        .forEach { println(it) }
}