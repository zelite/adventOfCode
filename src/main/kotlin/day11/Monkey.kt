package day11

class Monkey(monkeyDescription: List<String>) {
    private var divisibleBy: Int
    val falseTarget: Int
    val trueTarget: Int
    val divisibleTest: (Int) -> Boolean
    val holdingItems = ArrayDeque<Int>()
    val operation: (Int) -> Int
    var inspectionCount = 0L

    init {
        monkeyDescription[1].drop("  Starting items: ".length).split(", ").map { it.toInt() }
            .toCollection(holdingItems)

        operation = parseOperation(monkeyDescription[2])
        divisibleBy = monkeyDescription[3].drop("  Test: divisible by ".length).toInt()
        divisibleTest = { x -> x % divisibleBy == 0 }
        trueTarget = monkeyDescription[4].drop("    If true: throw to monkey ".length).toInt()
        falseTarget = monkeyDescription[5].drop("    If false: throw to monkey ".length).toInt()
    }

    private fun parseOperation(s: String): (Int) -> Int {
        val (_, operationString, secondOperandString) = s.drop("  Operation: new = ".length)
            .split(" ")
        //assuming first operand is always "old"
        //assuming only + and * operations exist
        val op: (Int, Int) -> Int =
            if (operationString == "+") { a, b -> a + b } else { a, b -> a * b }
        //second operator can be old or a number
        val secondOp: (Int) -> Int =
            if (secondOperandString == "old") { x -> x } else { x -> secondOperandString.toInt() }
        return { old -> op(old, secondOp(old)) }
    }


    fun inspectItem(): Pair<Int, Int>? {
        if (holdingItems.isEmpty()) {
            return null
        }
        inspectionCount++
        var itemToInspect = holdingItems.removeFirst()
        itemToInspect = operation(itemToInspect)
        itemToInspect = itemToInspect / 3 // that was for part 1
        return if (divisibleTest(itemToInspect)) {
            Pair(trueTarget, itemToInspect)
        } else {
            Pair(falseTarget, itemToInspect)
        }
    }

    fun giveItem(newItem: Int) {
        holdingItems.addLast(newItem)
    }

    override fun toString(): String {
        return "Monkey(falseTarget=$falseTarget, trueTarget=$trueTarget, divisibleTest=$divisibleTest, " +
                "holdingItems=$holdingItems, operation=$operation, inspectionCount=$inspectionCount)"
    }

}