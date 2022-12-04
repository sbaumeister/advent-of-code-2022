fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach { line ->
            val (range1, range2) = line.split(",").map { assignment ->
                assignment.split("-").map { it.toInt() }.let { (it.first()..it.last()) }
            }
            if ((range1.first <= range2.first && range1.last >= range2.last)
                || (range2.first <= range1.first && range2.last >= range1.last)
            ) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.forEach { line ->
            val (range1, range2) = line.split(",").map { assignment ->
                assignment.split("-").map { it.toInt() }.let { (it.first()..it.last()) }
            }
            if (range1.intersect(range2).isNotEmpty()) {
                count++
            }
        }
        return count
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
