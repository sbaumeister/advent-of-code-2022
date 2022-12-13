data class Graph(
    val nodes: MutableList<Pair<Int, Int>>,
    var endNode: Pair<Int, Int>,
    var startNode: Pair<Int, Int>,
    val heights: MutableList<MutableList<Int>>,
    val distances: MutableList<MutableList<Int>>,
    val predecessors: MutableList<MutableList<Pair<Int, Int>?>>,
)

fun charToHeight(item: Char): Int {
    return when (item) {
        'a' -> 1
        'b' -> 2
        'c' -> 3
        'd' -> 4
        'e' -> 5
        'f' -> 6
        'g' -> 7
        'h' -> 8
        'i' -> 9
        'j' -> 10
        'k' -> 11
        'l' -> 12
        'm' -> 13
        'n' -> 14
        'o' -> 15
        'p' -> 16
        'q' -> 17
        'r' -> 18
        's' -> 19
        't' -> 20
        'u' -> 21
        'v' -> 22
        'w' -> 23
        'x' -> 24
        'y' -> 25
        'z' -> 26
        'E' -> 26
        'S' -> 1
        else -> throw IllegalArgumentException()
    }
}

fun createGraph(input: List<String>): Graph {
    val graph = Graph(
        mutableListOf(),
        0 to 0,
        0 to 0,
        MutableList(input.size) { mutableListOf() },
        MutableList(input.size) { mutableListOf() },
        MutableList(input.size) { mutableListOf() },
    )

    repeat(input.size) { i ->
        val chars = input[i].toCharArray()
        chars.forEachIndexed { j, chr ->
            graph.nodes.add(i to j)
            graph.heights[i].add(charToHeight(chr))
            graph.distances[i].add(
                if (chr == 'S') {
                    graph.startNode = i to j
                    0
                } else Int.MAX_VALUE
            )
            graph.predecessors[i].add(null)
            if (chr == 'E') {
                graph.endNode = i to j
            }
        }
    }

    return graph
}

fun shortestPaths(graph: Graph, constraint: (height: Int, neighbourHeight: Int) -> Boolean) {
    while (graph.nodes.isNotEmpty()) {
        val node = graph.nodes.minBy { (i, j) -> graph.distances[i][j] }
        graph.nodes.remove(node)
        val (i, j) = node
        val currentHeight = graph.heights[i][j]
        val neighbourNodes = setOfNotNull(
            graph.nodes.firstOrNull { it == i + 1 to j },
            graph.nodes.firstOrNull { it == i - 1 to j },
            graph.nodes.firstOrNull { it == i to j + 1 },
            graph.nodes.firstOrNull { it == i to j - 1 },
        )
        neighbourNodes.forEach { (x, y) ->
            if (constraint(
                    currentHeight,
                    graph.heights[x][y]
                ) && graph.distances[i][j] + 1 < graph.distances[x][y]
            ) {
                graph.distances[x][y] = graph.distances[i][j] + 1
                graph.predecessors[x][y] = i to j
            }
        }
    }
}

fun shortestPathStepsTo(node: Pair<Int, Int>, graph: Graph): List<Pair<Int, Int>> {
    var currentNode: Pair<Int, Int>? = node
    val path = mutableListOf<Pair<Int, Int>>()
    while (currentNode != null) {
        path.add(currentNode)
        val (i, j) = currentNode
        currentNode = graph.predecessors.getOrNull(i)?.getOrNull(j)
    }
    return path
}

fun main() {
    fun part1(input: List<String>): Int {
        val graph = createGraph(input)
        shortestPaths(graph) { height, neighbourHeight -> neighbourHeight - height <= 1 }
        return shortestPathStepsTo(graph.endNode, graph).size - 1
    }

    fun part2(input: List<String>): Int {
        val graph = createGraph(input)
        graph.startNode.let { (i, j) -> graph.distances[i][j] = Int.MAX_VALUE }
        graph.endNode.let { (i, j) -> graph.distances[i][j] = 0 }
        shortestPaths(graph) { height, neighbourHeight -> neighbourHeight - height >= -1 }
        val paths = mutableListOf<List<Pair<Int, Int>>>()
        graph.heights.forEachIndexed { i, heights ->
            heights.forEachIndexed { j, height -> if (height == 1) paths.add(shortestPathStepsTo(i to j, graph)) }
        }
        return paths.filter { it.last() == graph.endNode }.minOf { it.size - 1 }
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
