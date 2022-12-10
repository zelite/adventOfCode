package day9

class Instruction(instruction: String) {
    val direction: Direction
    val nSteps: Int

    init {
        val (directionString, nStepsString) = instruction.split(" ")
        direction = Direction.from(directionString)!!
        nSteps = nStepsString.toInt()
    }

    fun performInstruction(k: Knot, afterStep: () -> Unit) {
        repeat(nSteps) {
            k.move(direction)
            afterStep()
        }
    }
}