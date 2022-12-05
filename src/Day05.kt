fun readStacks(input: List<String>, stackNumLine: String): List<ArrayDeque<Char>> {
    val stackNums = stackNumLine.toCharArray()
    val countStacks = stackNums.filterNot { it == ' ' }.last().toString().toInt()
    val stacks = MutableList(countStacks) { ArrayDeque<Char>() }
    input.forEach { line ->
        line.toCharArray().forEachIndexed { i, chr ->
            if (listOf('[', ']', ' ').contains(chr).not()) {
                val stackNum = stackNums[i].toString().toInt() - 1
                if (stackNum >= 0) {
                    stacks[stackNum].addLast(chr)
                }
            }
        }
    }
    return stacks
}

fun main() {
    fun part1(input: List<String>): String {
        val stackInput = input.takeWhile { it.isNotEmpty() }
        val stacks = readStacks(stackInput.dropLast(1), stackInput.last())
        input.drop(stackInput.size + 1).forEach { line ->
            val match = """move\s(\d+)\sfrom\s(\d+)\sto\s(\d+)""".toRegex().find(line)
            val (numCrates, stackNumFrom, stackNumTo) = match!!.destructured
            repeat(numCrates.toInt()) {
                val firstChr = stacks[stackNumFrom.toInt() - 1].removeFirst()
                stacks[stackNumTo.toInt() - 1].addFirst(firstChr)
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stackInput = input.takeWhile { it.isNotEmpty() }
        val stacks = readStacks(stackInput.dropLast(1), stackInput.last())
        input.drop(stackInput.size + 1).forEach { line ->
            val match = """move\s(\d+)\sfrom\s(\d+)\sto\s(\d+)""".toRegex().find(line)
            val (numCrates, stackNumFrom, stackNumTo) = match!!.destructured
            (1..numCrates.toInt()).map { stacks[stackNumFrom.toInt() - 1].removeFirst() }.reversed().forEach {
                stacks[stackNumTo.toInt() - 1].addFirst(it)
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
