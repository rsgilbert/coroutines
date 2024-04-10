import kotlinx.coroutines.delay
import java.math.BigInteger

val fibonacci: Sequence<BigInteger> = sequence {
    var first = 1.toBigInteger()
    var second = 1.toBigInteger()

    while(true) {
        yield(first)
        val temp = first
        first = second
        second = first+temp
    }
}

fun main() {
    print(fibonacci.take(10).toList())
}