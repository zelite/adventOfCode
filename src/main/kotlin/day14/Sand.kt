package day14

data class Sand(override var x: Int = 500, override var y: Int = 0) : HasCoordinates {
    var atRest: Boolean = false
    fun move(objectsInWay: RockPath) {
        if (objectsInWay.isDownFree(this)) {
            y = y + 1
        } else if (objectsInWay.isDownLeftFree(this)) {
            y = y + 1
            x = x - 1
        } else if (objectsInWay.isDownRightFree(this)) {
            y = y + 1
            x = x + 1
        } else {
            atRest = true
        }
        if (objectsInWay.isBottomOfCave(this) || this.equals(Sand())) {
            atRest = true
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sand

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }


}