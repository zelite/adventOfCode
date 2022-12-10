package day10

class Cpu {
    private var register = 1
    var cycle = mutableListOf(register)

    fun performNoop() {
        cycle.add(register)
    }

    fun performAddX(x: Int) {
        performNoop()
        register += x
        performNoop()
    }

    fun getValueAtTimeX(x: Int): Int {
        return cycle[x]
    }

    override fun toString(): String {
        return cycle.toString()
    }
}