private fun part1(input: String): Int {
    return input.lines().map { l ->
        val s = mutableSetOf<Char>()
        l.substring(0, l.length / 2).forEach { c -> s.add(c) }
        val badItem = l.substring(l.length / 2).toCharArray().filter { c -> c in s }[0]
        if (badItem.isUpperCase()) {
            badItem.code - 'A'.code + 27
        } else {
            badItem.code - 'a'.code + 1
        }
    }.sum()
}

private fun part2(input: String): Int {
    val m = mutableMapOf<Char, Int>()
    return input.lines().mapIndexed { index, l ->
        l.toSet().forEach { c -> m[c] = m.getOrDefault(c, 0) + 1 }
        if (index % 3 == 2) {
            val badge = m.filter { (k, v) -> v == 3 }.toList()[0].first
            m.clear()
            if (badge.isUpperCase()) {
                badge.code - 'A'.code + 27
            } else {
                badge.code - 'a'.code + 1
            }
        } else {
            0
        }
    }.sum()
}

fun main() {
    val testInput = readInputAsText("Day03_test")
    part1(testInput).println()
    part2(testInput).println()
}
