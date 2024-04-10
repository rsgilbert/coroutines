import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyException : Throwable("Just an exception for testing")

suspend fun main3() {
    try {
        suspendCoroutine<Unit> { cont ->
            cont.resumeWithException(MyException())
        }
    }
    catch(e: MyException) {
        println("Caught")
        e.printStackTrace()
    }
}

suspend fun main4() {
    val u = requestUser2()
    println(u)
    println("End")
}
suspend fun requestUser2() : User {
    return suspendCancellableCoroutine<User> {cont ->
        val v  = System.currentTimeMillis().toInt()
        if(v % 2 == 0) {
            cont.resume(User(v.toString(), v.toString()))
        }
        else {
            val e = Exception("System millis ${v} is an odd number. Original is $v")
            cont.resumeWithException(e)
        }
    }
}

var continuation: Continuation<Unit>? = null

suspend fun suspendAndSetContinuation() {
    println("Suspending...")
    suspendCoroutine<Unit> { cont ->
        continuation = cont
    }
    println("Resumption has taken place")
    delay(100)
    println("coroutine delay finished")
}

suspend fun main() {
    println("Before")

    thread {
        println("First thread")
        Thread.sleep(1000)
        println("thread resuming")
        continuation?.resume(Unit)
        println("Resumed continuation")
        println("First thread ends here")
    }

    suspendAndSetContinuation()


    println("Jaguar")
    println("After")
}