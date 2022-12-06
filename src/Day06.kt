fun main() {
    fun part1(input: List<String>): Int {
        val chars = input.first().toCharArray().toList()
        repeat(chars.size) { i ->
            val endIdx = i + 4
            if (chars.subList(i, endIdx).distinct().size == 4) {
                return endIdx
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val chars = input.first().toCharArray().toList()
        repeat(chars.size) { i ->
            val endIdx = i + 14
            if (chars.subList(i, endIdx).distinct().size == 14) {
                return endIdx
            }
        }
        return 0
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 29)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
