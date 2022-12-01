fun main() {
    fun part1(input: List<String>): Int {
        var maxSumCalories = 0
        var sumCalories = 0
        input.forEach {
            if (it == "") {
                sumCalories = 0
            } else {
                sumCalories += it.toInt()
                if (sumCalories > maxSumCalories) {
                    maxSumCalories = sumCalories
                }
            }
        }
        return maxSumCalories
    }

    fun part2(input: List<String>): Int {
        val calorieSums = arrayListOf<Int>()
        var sumCalories = 0
        input.forEachIndexed { i, line ->
            if (line == "") {
                calorieSums.add(sumCalories)
                sumCalories = 0
            } else {
                sumCalories += line.toInt()
                // add last sum
                if (i == input.size - 1) {
                    calorieSums.add(sumCalories)
                }
            }
        }
        return calorieSums.sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
