import java.util.LinkedList

fun main() {
    val testInput = readInput("Day11_test")
    part1(testInput).println()
    part2(testInput).println()
}

data class Monkey(
    val items: MutableList<Long>,
    val op: Triple<String, Char, String>,
    val test: Long,
    val throwIfTrue: Int,
    val throwIfFalse: Int,
    var inspectionCnt: Int
)

private fun part1(input: List<String>): Long {
    val monkeys = parseInput(input)
    return common(monkeys, 20) { it / 3 }
}

private fun part2(input: List<String>): Long {
    val monkeys = parseInput(input)
    val coef: Long = monkeys.map { it.test }.reduce(Long::times)
    return common(monkeys, 10_000) { it % coef }
}

private fun common(monkeys: MutableList<Monkey>, rounds: Int, loseInterest: (Long) -> Long): Long {
    repeat(rounds) {
        (0 until monkeys.size).forEach { ind ->
            (0 until monkeys[ind].items.size).forEach {// inspect item
                val value = loseInterest(makeOperation(monkeys[ind].items[it], monkeys[ind].op))
                if (value % monkeys[ind].test == 0L) {
                    monkeys[monkeys[ind].throwIfTrue].items.add(value)
                } else {
                    monkeys[monkeys[ind].throwIfFalse].items.add(value)
                }
            }
            monkeys[ind].inspectionCnt += monkeys[ind].items.size
            monkeys[ind].items.clear()
        }
    }
    var res = 1L
    monkeys.map { it.inspectionCnt }.sortedDescending().take(2).forEach { res *= it }
    return res
}

private fun makeOperation(oldValue: Long, op: Triple<String, Char, String>): Long {
    val v1 = if (op.first == "old") {
        oldValue
    } else {
        op.first.toLong()
    }
    val v2 = if (op.third == "old") {
        oldValue
    } else {
        op.third.toLong()
    }
    return if (op.second == '+') {
        v1 + v2
    } else { //'*'
        v1 * v2
    }
}

private fun parseInput(input: List<String>): MutableList<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var items = mutableListOf<Long>()
    var op: Triple<String, Char, String>? = null
    var test: Long? = null
    var throwIfTrue: Int? = null
    var throwIfFalse: Int? = null
    input.forEach { s ->
        val parsedS = s.split(" ", ",", ":").filter { it.isNotBlank() }
        if (parsedS.isNotEmpty()) {
            when (parsedS[0]) {
                "Starting" -> {
                    (2 until parsedS.size).forEach { items.add(parsedS[it].toLong()) }
                }

                "Operation" -> {
                    op = Triple(parsedS[parsedS.size - 3], parsedS[parsedS.size - 2][0], parsedS[parsedS.size - 1])
                }

                "Test" -> {
                    test = parsedS[parsedS.size - 1].toLong()
                }

                "If" -> {
                    if (parsedS[1] == "true") {
                        throwIfTrue = parsedS[parsedS.size - 1].toInt()
                    } else {
                        throwIfFalse = parsedS[parsedS.size - 1].toInt()

                        monkeys.add(Monkey(items, op!!, test!!, throwIfTrue!!, throwIfFalse!!, 0))
                        items = LinkedList()
                    }
                }
            }
        }
    }

    return monkeys
}
