import kotlin.math.abs

fun moveKnot(knot: Pair<Int, Int>, knotToFollow: Pair<Int, Int>): Pair<Int, Int> {
    val xDiff = knotToFollow.first - knot.first
    val yDiff = knotToFollow.second - knot.second
    var (xNew, yNew) = knot
    if (abs(xDiff) + abs(yDiff) >= 3) {
        xNew = if (xDiff > 0) knot.first + 1 else knot.first - 1
        yNew = if (yDiff > 0) knot.second + 1 else knot.second - 1
    }
    if (abs(xDiff) == 2 && yDiff == 0) {
        xNew = if (xDiff > 0) knot.first + 1 else knot.first - 1
    }
    if (xDiff == 0 && abs(yDiff) == 2) {
        yNew = if (yDiff > 0) knot.second + 1 else knot.second - 1
    }
    return xNew to yNew
}

fun main() {
    fun part1(input: List<String>): Int {
        var head = 0 to 0
        var tail = 0 to 0
        val tailPositions = mutableSetOf<Pair<Int, Int>>()
        tailPositions.add(tail)
        input.forEach { line ->
            val direction = line.substring(0, 1)
            val steps = line.substring(2).toInt()
            repeat(steps) {
                when (direction) {
                    "R" -> head = head.first + 1 to head.second
                    "L" -> head = head.first - 1 to head.second
                    "U" -> head = head.first to head.second + 1
                    "D" -> head = head.first to head.second - 1
                }
                tail = moveKnot(tail, head)
                tailPositions.add(tail)
            }
        }
        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        var head = 0 to 0
        val knots = (1..9).map { 0 to 0 }.toMutableList()
        val tailPositions = mutableSetOf<Pair<Int, Int>>()
        tailPositions.add(knots.last())
        input.forEach { line ->
            val direction = line.substring(0, 1)
            val steps = line.substring(2).toInt()
            repeat(steps) {
                when (direction) {
                    "R" -> head = head.first + 1 to head.second
                    "L" -> head = head.first - 1 to head.second
                    "U" -> head = head.first to head.second + 1
                    "D" -> head = head.first to head.second - 1
                }
                repeat(knots.size) { i ->
                    val leadingKnot = if (i == 0) head else knots[i - 1]
                    knots[i] = moveKnot(knots[i], leadingKnot)
                }
                tailPositions.add(knots.last())
            }
        }
        return tailPositions.size
    }

    val part1 = part1(readInput("Day09_test"))
    check(part1 == 13)
    val part2 = part2(readInput("Day09_test2"))
    check(part2 == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
