import java.lang.IllegalArgumentException

fun mapItemToPriority(item: Char): Int {
    return when (item) {
        'a' -> 1
        'b' -> 2
        'c' -> 3
        'd' -> 4
        'e' -> 5
        'f' -> 6
        'g' -> 7
        'h' -> 8
        'i' -> 9
        'j' -> 10
        'k' -> 11
        'l' -> 12
        'm' -> 13
        'n' -> 14
        'o' -> 15
        'p' -> 16
        'q' -> 17
        'r' -> 18
        's' -> 19
        't' -> 20
        'u' -> 21
        'v' -> 22
        'w' -> 23
        'x' -> 24
        'y' -> 25
        'z' -> 26
        'A' -> 27
        'B' -> 28
        'C' -> 29
        'D' -> 30
        'E' -> 31
        'F' -> 32
        'G' -> 33
        'H' -> 34
        'I' -> 35
        'J' -> 36
        'K' -> 37
        'L' -> 38
        'M' -> 39
        'N' -> 40
        'O' -> 41
        'P' -> 42
        'Q' -> 43
        'R' -> 44
        'S' -> 45
        'T' -> 46
        'U' -> 47
        'V' -> 48
        'W' -> 49
        'X' -> 50
        'Y' -> 51
        'Z' -> 52
        else -> throw IllegalArgumentException()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach nextRucksack@{ itemStr ->
            val items = itemStr.toCharArray()
            val firstCompartment = items.take(items.size / 2)
            val secondCompartment = items.takeLast(items.size / 2)
            firstCompartment.forEach { item ->
                val matchedItem = secondCompartment.firstOrNull { item == it }
                if (matchedItem != null) {
                    sum += mapItemToPriority(matchedItem)
                    return@nextRucksack
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val groupCount = input.size / 3
        (1..groupCount).forEach nextGroup@{ i ->
            val startIdx = (i - 1) * 3
            val endIdx = (i * 3) - 1
            val (firstRucksack, secondRucksack, thirdRucksack) = input.slice(startIdx..endIdx)
            val secondRucksackItems = secondRucksack.toCharArray()
            val thirdRucksackItems = thirdRucksack.toCharArray()
            val firstRucksackItems = firstRucksack.toCharArray()
            firstRucksackItems.forEach { item ->
                val matchedItem = secondRucksackItems.firstOrNull { item == it }
                if (matchedItem != null) {
                    if (thirdRucksackItems.firstOrNull { item == it } != null) {
                        sum += mapItemToPriority(matchedItem)
                        return@nextGroup
                    }
                }
            }
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
