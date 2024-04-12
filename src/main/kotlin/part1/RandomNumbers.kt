package part1

import kotlin.random.Random

fun randomNumbers(seed: Long = System.currentTimeMillis()): Sequence<Int> =
    sequence {
        val random = Random(seed)
        while (true) {
            yield(random.nextInt())
        }
    }


fun main() {
    println(randomNumbers(89).take(10).toList())
}