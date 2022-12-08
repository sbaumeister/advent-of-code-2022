data class Directory(
    val name: String,
    val parentNode: Directory?,
    val files: MutableSet<File>,
    val dirs: MutableSet<Directory>,
    var size: Int = 0,
) {
    override fun toString(): String = "$parentNode -> $name"
    override fun hashCode(): Int = parentNode.hashCode() + name.hashCode()
}

data class File(val name: String, val size: Int, val parentNode: Directory)

fun calcDirSize(dir: Directory): Int {
    val fileSizeSum = dir.files.sumOf { it.size }
    val dirSizeSum = dir.dirs.sumOf { calcDirSize(it) }
    return fileSizeSum + dirSizeSum
}

fun findDirsWithMaxSize(dir: Directory, maxSize: Int = 100000): Set<Directory> {
    val result = mutableSetOf<Directory>()
    dir.size = calcDirSize(dir)
    if (dir.size <= maxSize) {
        result.add(dir)
    }
    dir.dirs.forEach {
        result.addAll(findDirsWithMaxSize(it, maxSize))
    }
    return result
}

fun findDirsWithMinSize(dir: Directory, minSize: Int): Set<Directory> {
    val result = mutableSetOf<Directory>()
    dir.size = calcDirSize(dir)
    if (dir.size >= minSize) {
        result.add(dir)
    }
    dir.dirs.forEach {
        result.addAll(findDirsWithMinSize(it, minSize))
    }
    return result
}

fun buildTree(input: List<String>): Directory {
    val rootDir = Directory("/", null, mutableSetOf(), mutableSetOf())
    var currentDir = rootDir
    input.drop(1).forEach { line ->
        if (line.startsWith("$ cd")) {
            val targetDirName = line.substring(5)
            currentDir = when (targetDirName) {
                ".." -> currentDir.parentNode!!
                else -> currentDir.dirs.first { it.name == targetDirName }
            }
        }
        if (line.startsWith("dir")) {
            currentDir.dirs.add(Directory(line.substring(4), currentDir, mutableSetOf(), mutableSetOf()))
        }
        val fileMatch = """(\d+)\s(.+)""".toRegex().find(line)
        if (fileMatch != null) {
            val (fileSize, fileName) = fileMatch.destructured
            currentDir.files.add(File(fileName, fileSize.toInt(), currentDir))
        }
    }
    return rootDir
}

fun main() {
    fun part1(input: List<String>): Int {
        val rootDir = buildTree(input)
        val dirs = findDirsWithMaxSize(rootDir)
        return dirs.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val rootDir = buildTree(input)
        val rootDirSize = calcDirSize(rootDir)
        val unusedSpace = 70000000 - rootDirSize
        val dirs = findDirsWithMinSize(rootDir, 30000000 - unusedSpace)
        return dirs.minBy { it.size }.size
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
