fun main() {
    fun part1(input: String): Int =
        input.split("\n\n").map { it.lines().map(String::toInt).sum() }.max()

    fun part2(input: String): Int =
        input.split("\n\n").map { it.lines().map(String::toInt).sum() }.sortedDescending().take(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInputAsText("Day01_test")
    part1(testInput1).println()

    val testInput2 = readInputAsText("Day01_test")
    part2(testInput2).println()
}
