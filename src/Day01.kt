import readInputInt
import readTestInputInt

fun main() {
    fun countIncreasedFn(prevValueArg: Int): (Int, Int) -> Int {
        var prevValue = prevValueArg
        return fun(counter: Int, value: Int): Int {
            val counter = if (value > prevValue)  counter + 1 else counter
            prevValue = value
            return counter
        }
    }

    fun countIncreasedDepths(data: List<Int>): Int {
        return data.slice(IntRange(1, data.size - 1)).fold(0, countIncreasedFn(data[0]))
    }

    fun part1(input: List<Int>): Int {
        return countIncreasedDepths(input)
    }

    fun part2(input: List<Int>): Int {
        return countIncreasedDepths(input.windowed(3,1).map({window: List<Int> -> window.sum()}))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInputInt("day1")
    check(part1(testInput) == 7)

    val input = readInputInt("Day01")
    println(part1(input))
    println(part2(input))
}