package part2

import kotlinx.coroutines.*
import javax.security.auth.callback.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

// using suspendCancellableCoroutine
suspend fun main() {
    coroutineScope {
        println("START")
        val job = Job()
        launch(job) {
            suspendCancellableCoroutine<Unit> { cont ->
                cont.invokeOnCancellation { th ->
                    println("in invokeOnCancellation: ${th?.message}")
//                    cont.resumeWithException(CancellationException(th?.message))
//                    throw CancellationException(th?.message)
                }
                repeat(5) {
                    if (cont.isActive) {
                        println("in repeat ${it + 1}")
                        Thread.sleep(200)
                        println("Printing ${it + 1}")
                    }
                }
                if (cont.isActive) cont.resume(Unit)


            }
        }

        delay(500)
        println("About to cancel")
//        job.complete()
        job.cancel()
        job.join()
        println("Cancelled successfully")
        println("END")
    }
}

// stopping the unstoppable
suspend fun main36() {
    coroutineScope {
        val job = Job()
        launch(job) {
            repeat(10) {
//                if(!isActive) {
//                    return@launch
//                }
                Thread.sleep(200)
                ensureActive()
//                yield()
                println("Printing $it")
            }
        }

        delay(1200)
        println("About to cancel")
        job.cancelAndJoin()
        println("Cancelled successfully")
        delay(500)

    }
}

// invokeOnCompletion
suspend fun main35() {
    coroutineScope {
        val parentJob = Job(coroutineContext.job)
        val j = launch(parentJob) {
            println("START")
            delay(1000)
            println("HELLO")
        }

        j.invokeOnCompletion { e ->
            println("Finished")
            e?.printStackTrace()
        }


        delay(1500)
        parentJob.complete()
        j.cancelAndJoin()
        println("END")
//        j.invokeOnCompletion { e ->
//            println("Finished")
//            e?.printStackTrace()
//        }

    }
}


// NonCancellable job
suspend fun main34() {
    coroutineScope {
        val job = launch {
            try {
                delay(200)
                println("Coroutine finished")
            } finally {
                println("Finally")
                withContext(NonCancellable) {
                    delay(500)
                    println("Cleanup done")
                }
            }
        }
        delay(100)
        job.cancelAndJoin()
        println("END")
    }


}


// catch exception
suspend fun main33() {
    coroutineScope {
        val j = launch {
            try {
                val l = Random.nextLong(200)
                println("Will delay for $l ms")
                delay(l)
                println("Finished delaying for $l ms")
            } finally {
                println("Finally finished with this job")
                launch {
                    println("hello")
                }
                delay(100)
                println("gg")
            }
        }
        delay(100)
        j.cancelAndJoin()
    }
}

suspend fun main32() {
    coroutineScope {
        val job = Job()
        launch(job) {
            try {
                repeat(500) {
                    delay(200)
                    println("Printing $it")
                }
            } catch (e: CancellationException) {
                println("Caught Cancellation:")
                println(e)
                throw e
            }
        }
        delay(1000)
        job.cancelAndJoin()
        println("Cancelled successfully")
//        delay(200)
    }
}

// cancelAndJoin
suspend fun main31() {
    coroutineScope {
        val job = launch {
            repeat(500) {
                delay(100)
                Thread.sleep(100) // simulate long operation
                println("Printing $it")
            }
        }

        delay(1000)
        job.cancelAndJoin()
//        job.join()
        println("Cancelled successfully") // without job.join, will print before cancellation is complete
    }
}

suspend fun main30() {
    coroutineScope {
        val job = launch {
            repeat(1_000) { i ->
                println("before delay: here is i $i")
                delay(400)
                println("after delay: Printing $i")
            }
        }
        delay(1000)
        job.cancel(CancellationException("we want to cancel"))
        job.join()
        println("Cancelled successfully")
    }
}