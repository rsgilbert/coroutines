package part1

import kotlin.random.Random

fun randomUniqueStrings(
    length: Int,
    seed: Long = System.currentTimeMillis()
) : Sequence<String> = sequence<String> {
    val random = Random(seed)
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    println(charPool)

    while(true)
    {
        val s: String = (0..length)
            .map { random.nextInt(charPool.size)}
            .map ( charPool::get )
            .joinToString ("")
        yield(s)
    }
}.distinct()

fun main() {
    randomUniqueStrings(5).take(10).forEach {  println(it) }
}