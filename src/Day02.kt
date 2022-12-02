enum class Symbol { ROCK, PAPER, SCISSOR }

fun playRockPaperScissorRound(opponent: Symbol, me: Symbol): Int {
    // Opponent wins
    if ((opponent == Symbol.ROCK && me == Symbol.SCISSOR)
        || (opponent == Symbol.PAPER && me == Symbol.ROCK)
        || (opponent == Symbol.SCISSOR && me == Symbol.PAPER)
    ) {
        return me.ordinal + 1
    }

    // Me wins
    if ((me == Symbol.ROCK && opponent == Symbol.SCISSOR)
        || (me == Symbol.PAPER && opponent == Symbol.ROCK)
        || (me == Symbol.SCISSOR && opponent == Symbol.PAPER)
    ) {
        return 6 + me.ordinal + 1
    }

    // Draw
    return 3 + me.ordinal + 1
}

fun mapCharToSymbol(ch: Char): Symbol = when (ch) {
    'A', 'X' -> Symbol.ROCK
    'B', 'Y' -> Symbol.PAPER
    'C', 'Z' -> Symbol.SCISSOR
    else -> throw IllegalStateException()
}
fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val (opponentChar, meChar) = it.split(" ", limit = 2).map { str -> str.toCharArray().first() }
            sum += playRockPaperScissorRound(mapCharToSymbol(opponentChar), mapCharToSymbol(meChar))
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val (opponentChar, meChar) = it.split(" ", limit = 2).map { str -> str.toCharArray().first() }
            val opponentSymbol = mapCharToSymbol(opponentChar)
            val meSymbol = when (meChar) {
                // Loose
                'X' -> {
                    when (opponentSymbol) {
                        Symbol.ROCK -> Symbol.SCISSOR
                        Symbol.PAPER -> Symbol.ROCK
                        Symbol.SCISSOR -> Symbol.PAPER
                    }
                }
                // Draw
                'Y' -> opponentSymbol
                // Win
                'Z' -> {
                    when (opponentSymbol) {
                        Symbol.ROCK -> Symbol.PAPER
                        Symbol.PAPER -> Symbol.SCISSOR
                        Symbol.SCISSOR -> Symbol.ROCK
                    }
                }
                else -> throw IllegalStateException()
            }
            sum += playRockPaperScissorRound(opponentSymbol, meSymbol)
        }
        return sum
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
