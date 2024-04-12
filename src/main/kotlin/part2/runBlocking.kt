package part2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main3() {
    println("Started")
    runBlocking {
        println("runBlocking 1")
        delay(500)
        println("World 1")
    }
    println("After runBlocking 1")

    runBlocking {
        println("runBlocking 2")
        delay(500)
        println("World 2")
    }

    println("After runBlocking 2")

    runBlocking {
        println("runBlocking 3")
        delay(200)
        println("World 3")
    }

    println("End")
}

fun main5() {
    Thread.sleep(1000)
    println("Hello")

    runBlocking {
        delay(100)
        println("My dear")
    }
    Thread.sleep(400)
    println("World")

    Thread.sleep(300)
    println("Goodnight")
    println("Everybody")
}

fun main6() = runBlocking {
    println("start")
    delay(200)
    println("end")
}

fun main7() {
    runBlocking {
        GlobalScope.launch {
            println("First scope")
            delay(500)
            println("World 1")
        }

        GlobalScope.launch {
            println("Second scope")
            delay(500)
            println("World 2")
        }

        GlobalScope.launch {
            println("Third scope")
            delay(500)
            println("World 3")
        }

        println("Hello")
        delay(499)
    }
}

suspend fun main() {
    GlobalScope.launch {
        println("launched 1")
        delay(50)
        println("World 1")
    }

    GlobalScope.launch {
        println("launched 2")
        delay(80)
        println("World 2")
    }

    GlobalScope.launch {
        println("launched 3")
        delay(10)
        println("World 3")
    }

    println("Hello")
    delay(80)
}