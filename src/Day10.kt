fun main() {
    fun part1(input: List<String>): Int {
        var registerX = 1
        var instructionPointer = 0
        var instructionCyclesLeftToComplete = 0
        var cycle = 0
        var signalStrenghtSum = 0

        while (instructionPointer < input.size) {
            cycle++

            val instruction = input[instructionPointer].take(4)
            if (instructionCyclesLeftToComplete == 0) {
                when (instruction) {
                    "addx" -> instructionCyclesLeftToComplete = 2
                    "noop" -> instructionCyclesLeftToComplete = 1
                }
            }

            if (cycle == 20 || (cycle > 20 && ((cycle - 20) % 40 == 0))) {
                signalStrenghtSum += cycle * registerX
            }

            instructionCyclesLeftToComplete--

            if (instructionCyclesLeftToComplete == 0) {
                when (instruction) {
                    "addx" -> registerX += input[instructionPointer].substring(5).toInt()
                }
                instructionPointer++
            }
        }

        return signalStrenghtSum
    }

    fun part2(input: List<String>): String {

        var registerX = 1
        var instructionPointer = 0
        var instructionCyclesLeftToComplete = 0
        var cycle = 0
        val chars = mutableListOf<Char>()

        while (instructionPointer < input.size) {
            cycle++

            val instruction = input[instructionPointer].take(4)
            if (instructionCyclesLeftToComplete == 0) {
                when (instruction) {
                    "addx" -> instructionCyclesLeftToComplete = 2
                    "noop" -> instructionCyclesLeftToComplete = 1
                }
            }

            val pos = (cycle - 1) % 40
            if (pos >= registerX - 1 && pos <= registerX + 1) {
                chars.add('#')
            } else {
                chars.add('.')
            }

            if (cycle % 40 == 0) {
                chars.add('\n')
            }

            instructionCyclesLeftToComplete--

            if (instructionCyclesLeftToComplete == 0) {
                when (instruction) {
                    "addx" -> registerX += input[instructionPointer].substring(5).toInt()
                }
                instructionPointer++
            }
        }

        return chars.joinToString("")
    }

    val testInput = readInput("Day10_test")
    val part1 = part1(testInput)
    check(part1 == 13140)
    print(part2(testInput))

    val input = readInput("Day10")
    println(part1(input))
    print(part2(input))
}
