import java.util.Stack

fun main() {
    val testInput = readInput("Day05_test")
    part1(testInput).println()
    part2(testInput).println()
}

private fun part1(input: List<String>): String {
    return common(input, ::moveElement1)
}

private fun part2(input: List<String>): String {
    return common(input, ::moveElement2)
}

private fun common(input: List<String>, moveElement: (stacks: List<Stack<Char>>, cnt: Int, from: Int, to: Int) -> Unit): String {
    val stackNums = getStackNums(input)
    val stackNumsIndex = input.indexOf(stackNums)

    val stacks = List(STACK_CNT) { Stack<Char>() }

    (0..stackNumsIndex).forEach { ind ->
        input[ind].forEachIndexed { index, c ->
            if (c.isLetter()) {
                val stackInd = stackNums[index].digitToInt() - 1
                stacks[stackInd].push(c)
            }
        }
    }
    stacks.map { st -> st.reverse() }

    (stackNumsIndex + 2 until input.size).forEach { ind ->
        val parsedMove = input[ind].split("move", " ", "from", "to").toMutableList()
        parsedMove.removeIf { it.isBlank() }
        moveElement(stacks, parsedMove[0].toInt(), parsedMove[1].toInt(), parsedMove[2].toInt())
    }

    val sb = StringBuilder()
    stacks.filter { it.isNotEmpty() }.map { it.peek() }.forEach { sb.append(it) }
    return sb.toString()
}

private fun moveElement1(stacks: List<Stack<Char>>, cnt: Int, from: Int, to: Int) {
    repeat(cnt) {
        stacks[from - 1].pop().let { stacks[to - 1].push(it) }
    }
}

private fun moveElement2(stacks: List<Stack<Char>>, cnt: Int, from: Int, to: Int) {
    val helper = List(cnt) { stacks[from - 1].pop() }.reversed()
    helper.forEach { stacks[to - 1].push(it) }
}

private fun getStackNums(input: List<String>): String {
    return input.filter { l -> l.replace(" ", "").toBigIntegerOrNull() != null }[0]
}

const val STACK_CNT = 9
