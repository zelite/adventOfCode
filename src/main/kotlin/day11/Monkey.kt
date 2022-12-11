package day11

class Monkey(monkeyDescription: List<String>) {
    var reduceWorryFactor: Long = 0
    var divisibleBy: Long
    private val falseTarget: Int
    private val trueTarget: Int
    val divisibleTest: (Long) -> Boolean
    private val holdingItems = ArrayDeque<Long>()
    val operation: (Long) -> Long
    var inspectionCount = 0L

    init {
        monkeyDescription[1].drop("  Starting items: ".length).split(", ").map { it.toLong() }
            .toCollection(holdingItems)

        operation = parseOperation(monkeyDescription[2])
        divisibleBy = monkeyDescription[3].drop("  Test: divisible by ".length).toLong()
        divisibleTest = { x -> x % divisibleBy == 0L }
        trueTarget = monkeyDescription[4].drop("    If true: throw to monkey ".length).toInt()
        falseTarget = monkeyDescription[5].drop("    If false: throw to monkey ".length).toInt()
    }

    private fun parseOperation(s: String): (Long) -> Long {
        val (_, operationString, secondOperandString) = s.drop("  Operation: new = ".length)
            .split(" ")
        //assuming first operand is always "old"
        //assuming only + and * operations exist
        val op: (Long, Long) -> Long =
            if (operationString == "+") { a, b -> a + b } else { a, b -> a * b }
        //second operator can be old or a number
        val secondOp: (Long) -> Long =
            if (secondOperandString == "old") { x -> x } else { _ -> secondOperandString.toLong() }
        return { old -> op(old, secondOp(old)) }
    }


    fun inspectItem(): Pair<Int, Long>? {
        if (holdingItems.isEmpty()) {
            return null
        }
        inspectionCount++
        var itemToInspect = holdingItems.removeFirst()
        itemToInspect = operation(itemToInspect)
        //itemToInspect = itemToInspect / 3 // that was for part 1
        itemToInspect %= reduceWorryFactor
        return if (divisibleTest(itemToInspect)) {
            Pair(trueTarget, itemToInspect)
        } else {
            Pair(falseTarget, itemToInspect)
        }
    }

    fun giveItem(newItem: Long) {
        holdingItems.addLast(newItem)
    }

    override fun toString(): String {
        return "Monkey(falseTarget=$falseTarget, trueTarget=$trueTarget, divisibleTest=$divisibleTest, " +
                "holdingItems=$holdingItems, operation=$operation, inspectionCount=$inspectionCount)"
    }

}