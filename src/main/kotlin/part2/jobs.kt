package part2

import kotlinx.coroutines.*

suspend fun main() {
    coroutineScope {
        println("START")
        val parentJob = Job()
        val job = Job(parentJob)
        launch(job) {
            println("1 started")
            delay(600)
            println("hi 1")
        }
        val childJob = launch(job) {
            println("2 started")
            delay(800)
            println("hi 2")
        }
        parentJob.cancel()
        print("job Is Cancelled: ")
        println(job.isCancelled)
        print("parent job Is Cancelled: ")
        println(parentJob.isCancelled)
        print("child job Is Cancelled: ")
        println(childJob.isCancelled)
        job.join()
        delay(1000)
        println("END")

    }
}


// exception
fun main29() {
    runBlocking {
        val job = Job()

        launch(job) {
            repeat(5) {
                delay(200)
                println("Repeat $it")
            }
        }

        launch {
            delay(500)
            job.completeExceptionally(Error("Some test error"))
        }

        job.join()

        launch(job) {
            println("Will NOT be printer. Ever")
        }
        println("Done")
    }
}


// job.complete()
fun main28() {
    runBlocking {
        println("Started")
        val job = Job()


        launch(job) {
            repeat(5) {
                delay(200)
                println("Repeat ${it + 1}")
            }
        }

        launch {
            delay(500)
            print("Is completed: ")
            println(job.isCompleted)
            println("complete(): ${job.complete()}")
            print("Is completed: ")
            println(job.isCompleted)
        }

        job.join()

        launch(job) {
            println("Will not be printed")
        }
        println("Done")
    }
}

suspend fun main27() {
    coroutineScope {
        val job = Job()
        launch(job) {
            delay(1000)
            println("test 1")
        }
        launch(job) {
            delay(1000)
            println("test 2")
        }
        job.children.forEach { it.join() }
        println("Will  be printed")
    }
}

fun main26() {
    runBlocking {
        println("Start")
        val j1 = launch {
            delay(500)
            println("test job 1")
        }
        val j2 = launch {
            delay(400)
            println("test job 2")
        }

        coroutineContext.job.children.forEach { it.join() }
        println("All tests are done")
    }
}

fun main25() {
    runBlocking {
        val j1: Job = launch {
            delay(300)
        }
        val j2: Job = launch {
            delay(300)
        }
        println(j1)
        println(j2)
        val parentJob = coroutineContext.job
        println(parentJob == j1)
        println(parentJob.children.toList())

        val j3 = launch(coroutineContext.job) {
            println("job 3 started")
            delay(500)
            println("Hey there")
        }

        println("End")
//        delay(1000)
    }
}

fun main24() {
    runBlocking {
        val nm = CoroutineName("some name")
        val j = Job()
        println(nm)
        println(j)

        launch(nm + j) {
            val childName = coroutineContext[CoroutineName]
            println(childName)
            println(childName == nm)
            val childJob = coroutineContext[Job]
            println(childJob)
            println(childJob == j)
            val child = j.children.first()
            println(child)
            println(childJob == child)
            println(childJob?.parent)
            println(childJob?.parent == j)

        }
    }
}

fun main23() = runBlocking {
    println(coroutineContext[Job])
    println(coroutineContext.job.isActive)
}

fun main22(): Unit = runBlocking {
    val deferred: Deferred<String> = async {
        delay(500)
        "Trust"
    }
    val j: Job = deferred
    println(j.isActive)
    println(j)
    j.join()
    println(deferred)
    println(deferred.await())


}

suspend fun main21() {
    return coroutineScope {
        val job = Job()
        println(job)

        job.complete()
        println(job)


        val activeJob = launch {
            delay(200)
            println("delay complete")
        }
        println(activeJob)
        activeJob.join()
        println(activeJob)

        val lazyJob = launch(start = CoroutineStart.LAZY) {
            println("lazy job started")
            delay(1000)
            println("lazy job complete")
        }
        println(lazyJob)
        lazyJob.start()
        println(lazyJob)
        lazyJob.join()
        println(lazyJob)

    }
}

fun main20(): Unit = runBlocking(CoroutineName("main")) {
    val name = coroutineContext[CoroutineName]?.name
    print("Start: ")
    println(name)

    launch {
        delay(500)
        val name2 = coroutineContext[CoroutineName]?.name
        print("In launch: ")
        println(name2)
    }
}