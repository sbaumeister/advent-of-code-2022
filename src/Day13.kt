import kotlinx.serialization.json.*

fun listsInRightOrder(leftList: JsonArray, rightList: JsonArray): Boolean? {
    var i = 0
    while (true) {
        if (leftList.getOrNull(i) == null && rightList.getOrNull(i) == null) {
            return null
        }
        if (leftList.getOrNull(i) == null && rightList.getOrNull(i) != null) {
            return true
        }
        if (leftList.getOrNull(i) != null && rightList.getOrNull(i) == null) {
            return false
        }
        if (leftList[i] is JsonPrimitive && rightList[i] is JsonPrimitive) {
            if (leftList[i].jsonPrimitive.int < rightList[i].jsonPrimitive.int) {
                return true
            }
            if (leftList[i].jsonPrimitive.int > rightList[i].jsonPrimitive.int) {
                return false
            }
        }
        if (leftList[i] is JsonArray && rightList[i] is JsonArray) {
            listsInRightOrder(leftList[i].jsonArray, rightList[i].jsonArray)?.let { return it }
        }
        if (leftList[i] is JsonPrimitive && rightList[i] is JsonArray) {
            listsInRightOrder(buildJsonArray { add(leftList[i]) }, rightList[i].jsonArray)?.let { return it }
        }
        if (leftList[i] is JsonArray && rightList[i] is JsonPrimitive) {
            listsInRightOrder(leftList[i].jsonArray, buildJsonArray { add(rightList[i]) })?.let { return it }
        }
        i++
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val indicesInRightOrder = mutableSetOf<Int>()
        input.filter { it.isNotEmpty() }.chunked(2).forEachIndexed { i, (left, right) ->
            val leftList = Json.parseToJsonElement(left).jsonArray
            val rightList = Json.parseToJsonElement(right).jsonArray
            listsInRightOrder(leftList, rightList)?.let { if (it) indicesInRightOrder.add(i + 1) }
        }
        return indicesInRightOrder.sum()
    }

    fun part2(input: List<String>): Int {
        val packets = input.filter { it.isNotEmpty() }.map { Json.parseToJsonElement(it).jsonArray }
        val divider1 = buildJsonArray { addJsonArray { add(2) } }
        val divider2 = buildJsonArray { addJsonArray { add(6) } }
        val sortedPackets = packets.plusElement(divider1).plusElement(divider2).sortedWith { left, right ->
            when (listsInRightOrder(left, right)) {
                null -> 0
                true -> -1
                else -> 1
            }
        }
        return (sortedPackets.indexOfFirst { it == divider1 } + 1) * (sortedPackets.indexOfFirst { it == divider2 } + 1)
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
