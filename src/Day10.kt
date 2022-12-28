fun main() {
    val testInput = readInput("Day10_test")
    part1(testInput).println()
    part2(testInput).forEachIndexed { ind, c ->
        print(c)
        if((ind + 1) % 40 == 0) {
            println()
        }
    }
}


private fun part1(input: List<String>): Int {
    val strength = MutableList(3 * input.size + 2) { -1 }
    common(input, strength)
    return indices.map {
        it * strength[it - 1]
    }.sum()
}

private fun transferValue(curInd: Int, strength: MutableList<Int>): Int {
    strength[curInd + 1] = strength[curInd]
    return curInd + 1
}

private fun part2(input: List<String>): String {
    val strength = MutableList(3 * input.size + 2) { -1 }
    val lastInd = common(input, strength)
    val sb = StringBuilder()
    (0 until lastInd).forEach { ind ->
        val value = ind % 40
        if(strength[ind] - 1 <= value && value <= strength[ind] + 1) {
            sb.append("#")
        } else {
            sb.append(".")
        }
    }
    return sb.toString()
}

private fun common(input: List<String>, strength: MutableList<Int>): Int {
    strength[0] = 1
    var curInd = 0
    input.forEach { s ->
        val parsedS = s.split(" ")
        if (parsedS[0] == "noop") {
            curInd = transferValue(curInd, strength)
        } else {
            val value = parsedS[1].toInt()
            repeat(2) {
                curInd = transferValue(curInd, strength)
            }
            strength[curInd] += value
        }
    }
    return curInd
}

val indices = listOf(20, 60, 100, 140, 180, 220)
