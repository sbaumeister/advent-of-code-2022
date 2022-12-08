fun createTreeMap(input: List<String>): MutableList<List<Int>> {
    val treeMap = mutableListOf<List<Int>>()
    input.forEach { line ->
        val treeHeights = line.toCharArray().map { it.toString().toInt() }
        treeMap.add(treeHeights)
    }
    return treeMap
}

fun isVisibleFromTop(treeMap: MutableList<List<Int>>, x: Int, y: Int): Boolean {
    val maxHeight = treeMap[y][x]
    repeat(y) { i ->
        if (treeMap[i][x] >= maxHeight) {
            return false
        }
    }
    return true
}

fun isVisibleFromBottom(treeMap: MutableList<List<Int>>, x: Int, y: Int): Boolean {
    val maxHeight = treeMap[y][x]
    repeat(treeMap.size - y - 1) { i ->
        if (treeMap[y + i + 1][x] >= maxHeight) {
            return false
        }
    }
    return true
}

fun isVisibleFromLeft(treeMap: MutableList<List<Int>>, x: Int, y: Int): Boolean {
    val maxHeight = treeMap[y][x]
    repeat(x) { i ->
        if (treeMap[y][i] >= maxHeight) {
            return false
        }
    }
    return true
}

fun isVisibleFromRight(treeMap: MutableList<List<Int>>, x: Int, y: Int): Boolean {
    val maxHeight = treeMap[y][x]
    repeat(treeMap[y].size - x - 1) { i ->
        if (treeMap[y][x + i + 1] >= maxHeight) {
            return false
        }
    }
    return true
}

fun calcViewDistanceUp(treeMap: MutableList<List<Int>>, x: Int, y: Int, maxHeight: Int): Int {
    var viewDistance = 0
    repeat(y) { i ->
        viewDistance++
        val height = treeMap[y - i - 1][x]
        if (height >= maxHeight) {
            return viewDistance
        }
    }
    return viewDistance
}

fun calcViewDistanceDown(treeMap: MutableList<List<Int>>, x: Int, y: Int, maxHeight: Int): Int {
    var viewDistance = 0
    repeat(treeMap.size - y - 1) { i ->
        viewDistance++
        val height = treeMap[y + i + 1][x]
        if (height >= maxHeight) {
            return viewDistance
        }
    }
    return viewDistance
}

fun calcViewDistanceLeft(treeMap: MutableList<List<Int>>, x: Int, y: Int, maxHeight: Int): Int {
    var viewDistance = 0
    repeat(x) { i ->
        viewDistance++
        val height = treeMap[y][x - i - 1]
        if (height >= maxHeight) {
            return viewDistance
        }
    }
    return viewDistance
}

fun calcViewDistanceRight(treeMap: MutableList<List<Int>>, x: Int, y: Int, maxHeight: Int): Int {
    var viewDistance = 0
    repeat(treeMap[y].size - x - 1) { i ->
        viewDistance++
        val height = treeMap[y][x + i + 1]
        if (height >= maxHeight) {
            return viewDistance
        }
    }
    return viewDistance
}

fun main() {
    fun part1(input: List<String>): Int {
        var countVisible = 0
        val treeMap = createTreeMap(input)
        treeMap.forEachIndexed { y, heights ->
            heights.forEachIndexed { x, _ ->
                if (y == 0 || y == treeMap.size - 1 || x == 0 || x == heights.size - 1) {
                    countVisible++
                } else {
                    if (isVisibleFromTop(treeMap, x, y) || isVisibleFromBottom(treeMap, x, y) ||
                        isVisibleFromLeft(treeMap, x, y) || isVisibleFromRight(treeMap, x, y)
                    ) {
                        countVisible++
                    }
                }
            }
        }
        return countVisible
    }

    fun part2(input: List<String>): Int {
        val scenicScores = mutableSetOf<Int>()
        val treeMap = createTreeMap(input)
        treeMap.forEachIndexed { y, heights ->
            heights.forEachIndexed { x, height ->
                val scenicScore = calcViewDistanceDown(treeMap, x, y, height) *
                        calcViewDistanceUp(treeMap, x, y, height) *
                        calcViewDistanceLeft(treeMap, x, y, height) *
                        calcViewDistanceRight(treeMap, x, y, height)
                scenicScores.add(scenicScore)
            }
        }
        return scenicScores.max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
