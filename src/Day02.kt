private fun calcPoints(myMove: Char, opMove: Char): Int {
    val pointsForAction = when (opMove) {
        sameMovesXA[myMove]!! -> { 3 }

        winnersXA[myMove]!! -> { 6 }

        else -> { 0 }
    }
    return pointsForAction + movePoints[myMove]!!
}

private fun part1(input: String) =
    input.lines().map { l -> calcPoints(l[2], l[0]) }.sum()

private fun part2(input: String): Int {
    return input.lines().map {l ->
        val myMove = when(l[2]) {
            'X' -> {
                winnersAX[l[0]]
            } // lose
            'Y' -> {
                sameMovesAX[l[0]]
            } // draw
            'Z' -> {
                losersAX[l[0]]
            } // win
            else -> {' '}
        }
        calcPoints(myMove!!, l[0])
    }.sum()
}

fun main() {
    val testInput = readInputAsText("Day02_test")
    part1(testInput).println()
    part2(testInput).println()
}

val movePoints = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
val winnersXA = mapOf('X' to 'C', 'Y' to 'A', 'Z' to 'B') // values for the victory of the key
val winnersAX = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
val losersAX = winnersXA.map { (k, v) -> v to k }.toMap()
val sameMovesXA = mapOf('X' to 'A', 'Y' to 'B', 'Z' to 'C')
val sameMovesAX = sameMovesXA.map { (k, v) -> v to k }.toMap()
