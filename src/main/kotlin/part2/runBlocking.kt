package part2

import kotlinx.coroutines.*

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

suspend fun main9() {
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

fun main10() = runBlocking {
    this.launch {
        delay(80)
        println("world 1")
    }

    launch {
        delay(20)
        println("world 2")
    }

    launch {
        delay(400)
        println("world 3")
    }

    runBlocking {
        println("inner run blocking")
        delay(500)
        println("inner delayed")
    }
    println("hello")
}

fun main11() = runBlocking {
    println("Start")

    launch {
        println("launch 1")
        delay(100)
        runBlocking {
            println("launch 1 run blocking")
            delay(1000)
            println("finished launch 1 run blocking")
        }
        println("launch 1 done")
    }

    launch {
        println("launch 2")
        delay(100)
        runBlocking {
            println("launch 2 run blocking")
            delay(1000)
            println("finished launch 2 run blocking")
        }
        println("launch 2 done")
    }

    println("End")
}

fun main12() = runBlocking {
    val articles = getArticles(this)
    println(articles)

    println("Done")
}

suspend fun getArticles(sc: CoroutineScope) : String {
    val v = sc.async {
        delay(1000)
          "First article here"
    }
    return v.await()
//    return coroutineScope {
//        delay(1500)
//      return  "First article"
//    }
}

suspend fun main() : Unit = coroutineScope {
    launch {
        delay(400)
        println("World")
    }
    print("Hello ")
}