private fun isCovered(from1: Int, to1: Int, from2: Int, to2: Int): Boolean {
    return (from1 <= from2 && to1 >= to2) || (from2 <= from1 && to2 >= to1)
}

private fun part1(input: List<String>): Int {
    return input.filter { l ->
        val borders = l.split("-", ",").map(String::toInt)
        isCovered(borders[0], borders[1], borders[2], borders[3])
    }.count()
}

private fun isOverlaped(from1: Int, to1: Int, from2: Int, to2: Int): Boolean {
    return !(to1 < from2 || to2 < from1)
}

private fun part2(input: List<String>): Int {
    return input.filter { l ->
        val borders = l.split("-", ",").map(String::toInt)
        isOverlaped(borders[0], borders[1], borders[2], borders[3])
    }.count()
}

fun main() {
    val testInput = readInput("Day04_test")
//    part1(testInput).println()
    part2(testInput).println()
}