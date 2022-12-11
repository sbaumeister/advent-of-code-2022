enum class Operator {
    ADD, MULTIPLY,
}

class Monkey(
    private val id: Long,
    private val items: ArrayDeque<Long>,
    private val operation: Pair<Operator, Long?>,
    private val testDivisibleByValue: Long,
    private val testSuccessMonkeyId: Long,
    private val testFailureMonkeyId: Long,
) {
    var inspectedItemsCount = 0
    fun turn(monkeys: List<Monkey>, damageRelief: Boolean = true) {
        repeat(items.size) {
            val item = items.removeFirst()
            val (op, opVal) = operation
            var newItem = when (op) {
                Operator.ADD -> item + (opVal ?: item)
                Operator.MULTIPLY -> item * (opVal ?: item)
            }
            if (damageRelief) {
                newItem = newItem.floorDiv(3)
            } else {
                newItem %= monkeys.map { it.testDivisibleByValue }.reduce { acc, test -> acc * test }
            }
            if (newItem % testDivisibleByValue == 0L) {
                monkeys.first { it.id == testSuccessMonkeyId }.items.addLast(newItem)
            } else {
                monkeys.first { it.id == testFailureMonkeyId }.items.addLast(newItem)
            }
            inspectedItemsCount++
        }
    }
}

fun createMonkeys(input: List<String>): MutableList<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    input.chunked(7).forEach { block ->
        val monkeyId = block[0].substringAfter("Monkey ").dropLast(1).toLong()

        val items = block[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }
        val itemDeque = ArrayDeque(items)

        val (opStr, valStr) = """old\s([+*])\s(\d+|old)""".toRegex()
            .find(block[2].substringAfter("= "))!!.destructured
        val op = when (opStr) {
            "*" -> Operator.MULTIPLY
            "+" -> Operator.ADD
            else -> throw IllegalArgumentException()
        }
        val opVal = if (valStr == "old") null else valStr.toLong()

        val testValue = block[3].substringAfter("by ").toLong()

        val testTrueId = block[4].substringAfter("monkey ").toLong()
        val testFalseId = block[5].substringAfter("monkey ").toLong()

        monkeys.add(Monkey(monkeyId, itemDeque, op to opVal, testValue, testTrueId, testFalseId))
    }
    return monkeys
}

fun main() {
    fun part1(input: List<String>): Int {
        val monkeys = createMonkeys(input)
        repeat(20) {
            monkeys.forEach { monkey -> monkey.turn(monkeys) }
        }
        return monkeys.map { it.inspectedItemsCount }.sorted().takeLast(2).reduce { m1, m2 -> m1 * m2 }
    }

    fun part2(input: List<String>): Long {
        val monkeys = createMonkeys(input)
        repeat(10000) {
            monkeys.forEach { monkey -> monkey.turn(monkeys, false) }
        }
        return monkeys.map { it.inspectedItemsCount.toLong() }.sorted().takeLast(2).reduce { m1, m2 -> m1 * m2 }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
