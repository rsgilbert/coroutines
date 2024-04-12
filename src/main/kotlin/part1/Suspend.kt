package part1

import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


val executor: ScheduledExecutorService =
    Executors.newSingleThreadScheduledExecutor {
        val t= Thread(it, "scheduler")
            t.apply { isDaemon = true }
        println(t)
        t
    }

suspend fun myDelay(timeMillis: Long) : Unit {
    suspendCoroutine<Unit> { cont ->
        executor.schedule({
            cont.resume(Unit)
            println("delayed")
        }, timeMillis, TimeUnit.MILLISECONDS)
    }
}

suspend fun main() {
    println("Before")

    myDelay(200)
    myDelay(1000)
    myDelay(800)

    delay(56)
    println("After")
}


suspend fun main2() {
    println("Before")

    // uses a new thread everytime
    suspendCoroutine { continuation ->
        continueAfterSecond(continuation)
    }
    suspendCoroutine { continuation ->
        continueAfterSecond(continuation)
    }
    suspendCoroutine { continuation ->
        continueAfterSecond(continuation)
    }
    println("After")
}

fun continueAfterSecond(continuation: Continuation<Unit>) {
    val t: Thread = thread {
        Thread.sleep(1000)
        continuation.resume(Unit)
    }
    println(t)
}