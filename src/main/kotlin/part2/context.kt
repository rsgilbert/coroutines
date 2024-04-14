package part2

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext


fun CoroutineScope.log(msg: String) {
    val nm = coroutineContext[CoroutineName]?.name
    println("[$nm] $msg")
}

suspend fun printName() {
    println(coroutineContext[CoroutineName]?.name)
}

class MyCustomContext: CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key

    companion object Key : CoroutineContext.Key<MyCustomContext>
}

class CounterContext(private val name: String): CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    private var nextNumber = 0

    fun printNextNumber() {
        println("$name: $nextNumber")
        nextNumber++
    }

    companion object Key : CoroutineContext.Key<CounterContext>
}

suspend fun printNext() {
    coroutineContext[CounterContext]?.printNextNumber()
}

suspend fun main() : Unit = withContext(CounterContext("Outer")) {

    launch {
        printNext()
        launch {
            printNext()
        }
        print("first launch")
        launch(CounterContext("Inner")) {
            printNext()
            printNext()
            launch {
                printNext()
            }
        }
        println("Hi")
    }
    print("last")
    printNext()
    println("Hello")
}

suspend fun main19() {
    return withContext(CoroutineName("Outer")) {
        printName()
        launch(CoroutineName("Inner")) {
            printName()
        }
    }
}


fun main18() = runBlocking(CoroutineName("main")) {
    log("Started")
    val v1 = async(CoroutineName("life")) {
        delay(200)
        log("Running async")
        50
    }

    launch(CoroutineName("missile")) {
        delay(500)
        log("Running launch")
    }
    log("The answer is ${v1.await()}")
}

fun main17() {
    val ctx = CoroutineName("name1") + Job()
    ctx.fold("") { acc, el -> "$acc$el, "}
        .also(::println)

    val v = ctx.fold(emptyList()) { acc: List<CoroutineContext>, el ->
        acc + el
    }
    println(v)
    val l = listOf("a", "b")  +  listOf("c", "d")
    println(l)

}

fun main16() {
    val ctx = CoroutineName("Name1") + Job()
    println(ctx[CoroutineName])
    println(ctx[Job])

    val ctx2 = ctx.minusKey(CoroutineName)
    println(ctx2[CoroutineName])
    println(ctx2[Job])


    val ctx3 = ctx2.minusKey(Job)
    println(ctx3[CoroutineName])
    println(ctx3[Job])


}

fun main15() {
    val empty = EmptyCoroutineContext
    println(empty[CoroutineName])
    println(empty[Job])

    val ctxName = empty + CoroutineName("Nm1") + empty + empty + EmptyCoroutineContext
    println(ctxName)
    println(ctxName[CoroutineName])
}
fun main14() {
    val ctx1 = CoroutineName("name1")
    println(ctx1[CoroutineName]?.name)

    val ctx2 = CoroutineName("name2")
    println(ctx2[CoroutineName]?.name)

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name)
}

fun main13() {
    val ctx: CoroutineContext = CoroutineName("a name")

    val coroutineName: CoroutineName? = ctx[CoroutineName]
    println(coroutineName?.name)
    println(coroutineName?.key)
    println(ctx[CoroutineName.Key])
    println(ctx[Job])


    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name)
    println(ctx2)
    println(ctx2[Job])
    println(ctx2[Job]?.isActive)

    val ctx3 = ctx + ctx2
    println(ctx3[CoroutineName])
    println(ctx3[Job])
    println(ctx3[Job]?.isActive)
}