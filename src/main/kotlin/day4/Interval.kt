package day4

class Interval(interval: String) {
    private val start: Int
    private val end: Int

    init {
        val splitInterval = interval.split("-")
        start = splitInterval[0].toInt()
        end = splitInterval[1].toInt()
    }

    fun fullOverlap(other: Interval): Boolean =
        start >= other.start && end <= other.end

    fun overlap(other: Interval):Boolean =
        start <= other.end && other.start <= this.end

}