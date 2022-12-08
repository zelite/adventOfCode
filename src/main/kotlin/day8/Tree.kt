package day8

data class Tree(val size: Int, val posX: Int, val posY: Int) {
    var scenicScore: Int = 0
    var visible: Boolean = false
    override fun toString(): String {
        return "Tree( size:$size, visible:$visible, x:$posX, y:$posY )"
    }
}
