package part2

import kotlinx.coroutines.*

suspend fun main8()  {
    val res1:  Deferred<Int> = GlobalScope.async {
        println("async 1")
        delay(400)
        println("has been delayed")
        20
    }

    val res2 = GlobalScope.async {
        println("async 2")
        "Jaguar"
    }

    val res3 = GlobalScope.async {
        println("async 3")
        delay(700)
        "Poku"
    }


    println("Other stuff")

//    val result : Int = res1.await()
//    println(result)
    println(res2.await())
    println(res3.await())
}

fun main() = runBlocking {
    // dont do this
    GlobalScope.async {
        delay(1000)
        println("World")
    }
    print("Hello ")
    delay(2000)
}