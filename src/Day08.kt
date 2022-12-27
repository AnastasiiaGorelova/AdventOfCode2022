fun main() {
    val testInput = readInput("Day08_test")
    part1(testInput).println()
    part2(testInput).println()
}

private fun part1(input: List<String>): Int {
    val n = input.size
    val m = input[0].length
    val trees = mutableSetOf<Pair<Int, Int>>()
    var maxi = -1
    input.forEachIndexed { lInd, l ->
        maxi = -1
        l.forEachIndexed { cInd, c ->
            if (c.digitToInt() > maxi) {
                maxi = c.digitToInt()
                trees.add(Pair(lInd, cInd))
            }
        }
        maxi = -1
        l.reversed().forEachIndexed { cInd, c ->
            if (c.digitToInt() > maxi) {
                maxi = c.digitToInt()
                trees.add(Pair(lInd, m - cInd - 1))
            }
        }
    }

    (0 until m).forEach { col ->
        val sb = StringBuilder()
        input.forEach { sb.append(it[col]) }
        val l = sb.toString()

        maxi = -1
        l.forEachIndexed { cInd, c ->
            if (c.digitToInt() > maxi) {
                maxi = c.digitToInt()
                trees.add(Pair(cInd, col))
            }
        }
        maxi = -1
        l.reversed().forEachIndexed { cInd, c ->
            if (c.digitToInt() > maxi) {
                maxi = c.digitToInt()
                trees.add(Pair(n - cInd - 1, col))
            }
        }
    }
    return trees.size
}

private fun part2(input: List<String>): Int {
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
    var res = 1

    input.forEachIndexed { ind1, s ->
        s.forEachIndexed { ind2, c ->
            var coef = 1
            directions.map {
                checkDirection(input, ind1, ind2, it)
            }.forEach {
                coef *= it
            }
            res = maxOf(res, coef)
        }
    }

    return res
}

private fun checkDirection(input: List<String>, ind1: Int, ind2: Int, dir: Pair<Int, Int>): Int {
    val value = input[ind1][ind2]
    var res = 0
    var currentCoords = Pair(ind1, ind2)
    while (currentCoords.first + dir.first >= 0 &&
        currentCoords.first + dir.first < input.size &&
        currentCoords.second + dir.second >= 0 &&
        currentCoords.second + dir.second < input[0].length
    ) {
        currentCoords = Pair(currentCoords.first + dir.first, currentCoords.second + dir.second)
        res++
        if (input[currentCoords.first][currentCoords.second] >= value) {
            break
        }
    }
    return res
}
