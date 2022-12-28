import java.lang.Math.abs

fun main() {
    val testInput = readInput("Day09_test")
    part1(testInput).println()
    part2(testInput).println()
}

private fun part1(input: List<String>) = common(input, 1)

private fun moveT(tCoord: Int, hCoord: Int): Int {
    return if (abs(tCoord - hCoord) <= 1) {
        hCoord
    } else if (hCoord > tCoord) {
        hCoord - 1
    } else {
        hCoord + 1
    }
}

private fun part2(input: List<String>) = common(input, 9)

private fun common(input: List<String>, len: Int): Int {
    val moves = mapOf("U" to Pair(-1, 0), "D" to Pair(1, 0), "R" to Pair(0, 1), "L" to Pair(0, -1))
    val rope = MutableList(len + 1) { Pair(0, 0) } // H at 0, T at 9
    val visited = mutableSetOf(Pair(0, 0))
    input.forEach { s ->
        val parsedS = s.split(" ")
        val move = moves[parsedS[0]]!!
        repeat(parsedS[1].toInt()) {
            rope[0] = Pair(rope[0].first + move.first, rope[0].second + move.second)
            (1..len).forEach {ind ->
                if (!(abs(rope[ind].first - rope[ind - 1].first) <= 1 && abs(rope[ind].second - rope[ind - 1].second) <= 1)) {
                    val fst = moveT(rope[ind].first, rope[ind - 1].first)
                    val snd = moveT(rope[ind].second, rope[ind - 1].second)
                    rope[ind] = Pair(fst, snd)
                }
                if(ind  == len) {
                    visited.add(rope[ind])
                }
            }
        }
    }
    return visited.size
}
