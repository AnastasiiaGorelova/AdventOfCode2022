fun main() {
    val testInput = readInputAsText("Day06_test")
    part1(testInput).println()
    part2(testInput).println()
}

private fun part1(input: String) = common(input, RANGE1)

private fun part2(input: String) = common(input, RANGE2)

private fun common(input: String, range: Int): Int {
    val fourChars = mutableMapOf<Char, Int>()
    input.forEachIndexed { index, c ->
        if(index - range >= 0) {
            val lElement = input[index - range]
            val cnt = fourChars.remove(lElement)!! - 1
            if(cnt > 0) {
                fourChars[lElement] = cnt
            }
        }

        val newElementCnt = fourChars.getOrDefault(c, 0) + 1
        fourChars[c] = newElementCnt
        if(fourChars.size == range) {
            return index + 1
        }
    }
    return -1
}

const val RANGE1 = 4
const val RANGE2 = 14
