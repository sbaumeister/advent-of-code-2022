data class Scan(
    var map: MutableList<MutableList<Char>>,
    var startPos: Pair<Int, Int>,
    val paths: List<List<Pair<Int, Int>>>,
    val highestX: Int,
    val lowestX: Int,
    val highestY: Int,
)

fun createScanPart1(input: List<String>): Scan {
    var (highestX, lowestX, highestY) = listOf(0, Int.MAX_VALUE, 0)
    val paths = input.map { line ->
        line.split(" -> ")
            .map { it.split(",").map { n -> n.toInt() } }
            .map { (x, y) ->
                if (x > highestX) highestX = x
                if (x < lowestX) lowestX = x
                if (y > highestY) highestY = y
                x to y
            }
    }.map { it.map { (x, y) -> y to (x - lowestX) } }
    val startPos = 0 to (500 - lowestX)
    return Scan(mutableListOf(), startPos, paths, highestX, lowestX, highestY)
}

fun createScanPart2(input: List<String>): Scan {
    var (highestX, lowestX, highestY) = listOf(0, Int.MAX_VALUE, 0)
    val paths = input.map { line ->
        line.split(" -> ")
            .map { it.split(",").map { n -> n.toInt() } }
            .map { (x, y) ->
                if (x > highestX) highestX = x
                if (x < lowestX) lowestX = x
                if (y > highestY) highestY = y
                x to y
            }
    }
    val newWidth = (highestY + 1 + 2) * 2 + 1
    val xMiddlePos = (newWidth - 1) / 2
    val xPosDiff = xMiddlePos - (500 - lowestX)
    val startPos = 0 to xMiddlePos
    return Scan(
        mutableListOf(),
        startPos,
        paths.map { it.map { (x, y) -> y to (x - lowestX) + xPosDiff } },
        lowestX + newWidth - 1,
        lowestX,
        highestY
    )
}

fun countRestingSandUnits(scan: Scan): Int {
    var countRestingUnits = 0
    while (true) {
        var pos = scan.startPos
        while (true) {
            val charDown = scan.map.getOrNull(pos.first + 1)?.getOrNull(pos.second) ?: return countRestingUnits
            val newPos = when (charDown) {
                '.' -> pos.first + 1 to pos.second
                '#', 'o' -> {
                    val charDownLeft =
                        scan.map.getOrNull(pos.first + 1)?.getOrNull(pos.second - 1) ?: return countRestingUnits
                    if (charDownLeft == '.') {
                        pos.first + 1 to pos.second - 1
                    } else {
                        val charDownRight =
                            scan.map.getOrNull(pos.first + 1)?.getOrNull(pos.second + 1) ?: return countRestingUnits
                        if (charDownRight == '.') {
                            pos.first + 1 to pos.second + 1
                        } else {
                            pos
                        }
                    }
                }

                else -> pos
            }
            if (newPos == pos) {
                newPos.let { (x, y) -> scan.map[x][y] = 'o' }
                countRestingUnits++
                if (newPos == scan.startPos) {
                    return countRestingUnits
                }
                break
            }
            pos = newPos
        }
    }
}

fun drawRocksOnMap(scan: Scan) {
    scan.paths.forEach { path ->
        path.windowed(2).forEach { (p1, p2) ->
            if (p1.first < p2.first) {
                (p1.first..p2.first).forEach { scan.map[it][p1.second] = '#' }
            }
            if (p1.first > p2.first) {
                (p2.first..p1.first).forEach { scan.map[it][p1.second] = '#' }
            }
            if (p1.second < p2.second) {
                (p1.second..p2.second).forEach { scan.map[p1.first][it] = '#' }
            }
            if (p1.second > p2.second) {
                (p2.second..p1.second).forEach { scan.map[p1.first][it] = '#' }
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val scan = createScanPart1(input)
        scan.map = MutableList(scan.highestY + 1) { x ->
            MutableList(scan.highestX - scan.lowestX + 1) { y ->
                if (x to y == scan.startPos) '+' else '.'
            }
        }
        drawRocksOnMap(scan)
        val countRestingSandUnits = countRestingSandUnits(scan)
        //scan.map.forEach { println(it.joinToString()) }
        return countRestingSandUnits
    }

    fun part2(input: List<String>): Int {
        val scan = createScanPart2(input)
        scan.map = MutableList(scan.highestY + 3) { x ->
            MutableList(scan.highestX - scan.lowestX + 1) { y ->
                if (x to y == scan.startPos) '+' else '.'
            }
        }
        drawRocksOnMap(scan)
        repeat(scan.map.last().size) { i ->
            scan.map.last()[i] = '#'
        }
        val countRestingSandUnits = countRestingSandUnits(scan)
        //scan.map.forEach { println(it.joinToString()) }
        return countRestingSandUnits
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
