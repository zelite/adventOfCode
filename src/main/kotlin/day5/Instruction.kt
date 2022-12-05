package day5

class Instruction(s: String) {
    fun executeInstruction(crates: Crates) {
        crates.moveFromTo(from - 1, to - 1, amount)
    }

    private val from: Int
    private val to: Int
    private val amount: Int

    init {
        val splitted = s.split(" ").filter { it.isNotBlank() }
        amount = splitted[0].toInt()
        from = splitted[1].toInt()
        to = splitted[2].toInt()
    }
}
