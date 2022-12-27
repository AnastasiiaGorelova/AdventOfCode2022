fun main() {
    val testInput = readInput("Day07_test")
    part1(testInput).println()
    part2(testInput).println()
}

data class DirOrFile(
    val name: String,
    val content: MutableSet<String>,
    val parent: String
)

private fun part1(input: List<String>): Int {
    val costs = mutableMapOf<String, Int>()
    val dirs = mutableMapOf<String, DirOrFile>() // only directories!
    val homeDir = DirOrFile("/", mutableSetOf(), "/")

    buildTree(input, homeDir, costs, dirs)
    return calcCosts(homeDir.name, costs, dirs)
}

private fun part2(input: List<String>): Int {
    val costs = mutableMapOf<String, Int>()
    val dirs = mutableMapOf<String, DirOrFile>() // only directories!
    val homeDir = DirOrFile("/", mutableSetOf(), "/")

    buildTree(input, homeDir, costs, dirs)
    calcCosts(homeDir.name, costs, dirs)
    val busySpace = costs[homeDir.name]!!
    val freeSpace = TOTAL_SPACE - busySpace
    val spaceToFree = SPACE_FOR_UPDATE - freeSpace
    return costs.filterKeys { dirs.containsKey(it) }.filterValues { it >= spaceToFree }.minBy { it.value }.value
}

private fun buildTree(
    input: List<String>,
    homeDir: DirOrFile,
    costs: MutableMap<String, Int>,
    dirs: MutableMap<String, DirOrFile>
) {
    dirs[homeDir.name] = homeDir
    var curDir = homeDir.name
    input.forEach { c ->
        val args = c.split(" ")
        if (args[0] == "$" && args[1] == "cd") {
            curDir = if (args[2] == "..") {
                dirs[curDir]!!.parent
            } else if (args[2] == "/") {
                homeDir.name
            } else {
                "${curDir}/${args[2]}"
            }
        }

        if (args[0] == "dir") {
            val nextDirName = "${curDir}/${args[1]}"
            val nextDir = DirOrFile(nextDirName, mutableSetOf(), curDir)
            dirs[nextDirName] = nextDir
            dirs[curDir]!!.content.add(nextDirName)
        }
        if (args[0].toIntOrNull() != null) {
            val nextDirName = "${curDir}/${args[1]}"
            costs[nextDirName] = args[0].toInt()
            dirs[curDir]!!.content.add(nextDirName)
        }
    }
}

private fun calcCosts(curDir: String, costs: MutableMap<String, Int>, dirs: MutableMap<String, DirOrFile>): Int {
    if (!dirs.containsKey(curDir)) {
        return 0
    }
    var acc = 0
    val curDirCost = dirs[curDir]!!.content.map { u ->
        acc += calcCosts(u, costs, dirs)
        costs[u]!!
    }.sum()
    costs[curDir] = curDirCost
    return if (curDirCost < TRESHOLD) {
        acc + curDirCost
    } else {
        acc
    }
}

const val TRESHOLD = 100000
const val TOTAL_SPACE = 70000000
const val SPACE_FOR_UPDATE = 30000000
