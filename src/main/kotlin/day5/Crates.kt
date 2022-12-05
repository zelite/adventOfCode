package day5

class Crates(cratesLines: List<String>) {
    private val cratesIndexes: List<Int> = (1..9).map { crateIndexToStringIndex(it) }
    private val crates: List<ArrayDeque<Char>> = List(9) { ArrayDeque() }

    init {
        cratesLines.map { it.slice(cratesIndexes) }.reversed().forEach { addToCrates(it) }
    }

    fun crateIndexToStringIndex(x: Int): Int = 4 * x - 3
    private fun addToCrates(line: String) {
        line.toCharArray().forEachIndexed { index, c -> addToCrate(c, index) }
    }

    fun addToCrate(toAdd: Char, index: Int) {
        if (!toAdd.isWhitespace()) {
            crates[index].addLast(toAdd)
        }
    }

    fun getTopLetters(): String {
        return crates.map { c -> c.lastOrNull() }.joinToString(separator = "")
    }

    fun moveFromTo(from: Int, to: Int, amount: Int = 1) {
        val temp = ArrayDeque<Char>()
        //part1 repeat(amount) { crates[to].addLast(crates[from].removeLast()) }
        repeat(amount) { temp.addLast(crates[from].removeLast()) }
        repeat(amount) { crates[to].addLast(temp.removeLast()) }
    }
}