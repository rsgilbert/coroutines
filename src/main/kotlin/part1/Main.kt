package part1


val seq = sequence {
    println("Yielding 1")
    yield(1)
    println("continuing")
    println("Yielding 2")
    yield(2)
    println("Yielding 3")
    yield(3)
}

fun main() {
    val iterator = seq.iterator()
    println("Starting")

    val f = iterator.next()
    println("First element is $f.")

    val s = iterator.next()
    println("Second element is $s")

    val t = iterator.next()
    println("Third element is $t.")

//    val ft = iterator.next()
//    println("Fourth element is $ft. Has next is ${iterator.hasNext()}")

//    for (num in part1.getSeq) {
//        println("Next number is $num")
//    }
}