package day3

class Backpack(items: String) {
    private var compartmentA: String
    private var compartmentB: String

    init {
        compartmentA = items.substring(0, items.length / 2)
        compartmentB = items.substring(items.length / 2, items.length)
    }

    fun getCommonItemBetweenCompartments(): Char {
        val commonChars = commonChars(compartmentA, compartmentB)
        if (commonChars.size > 1) {
            throw IllegalArgumentException()
        }
        return commonChars.first()
    }

    fun getBackpackWithCommonItems(otherBackpack: Backpack): Backpack {
        val thisBackpackItems = getContent()
        val otherBackItems = otherBackpack.getContent()
        return Backpack(commonChars(thisBackpackItems, otherBackItems).joinToString { t -> t.toString() })
    }

    private fun commonChars(a: String, b: String): Set<Char> {
        return a.toCharArray().intersect(b.toCharArray().toSet())
    }

    fun getContent(): String {
        return compartmentA + compartmentB
    }
}
