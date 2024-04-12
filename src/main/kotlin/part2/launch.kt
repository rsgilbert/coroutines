package part2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun main() {
    GlobalScope.launch {
        println("launched first global scope")
        delay(1000)
        println("world")
    }

    GlobalScope.launch {
        println("launched second global scope")
        delay(1000)
        println("Another world")
    }

    GlobalScope.launch {
        println("launched third global scope")
        delay(1000)
        println("third world")
    }
    println("Hello")
    Thread.sleep(1010)

}

fun main2() {
    // with daemon threads
    thread(isDaemon=true) {
        println("Starting first daemon thread")
        Thread.sleep(1000)
        println("First daemon thread")
    }

    thread(isDaemon=true) {
        println("Starting second daemon thread")
        Thread.sleep(1000)
        println("Second daemon thread")
    }
    thread(isDaemon=true) {
        println("Starting third daemon thread")
        Thread.sleep(1000)
        println("Third daemon thread")
    }


    println("Hello")
    Thread.sleep(1000)



}