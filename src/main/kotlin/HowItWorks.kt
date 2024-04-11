import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

val COROUTINE_SUSPENDED = "COROUTINE_SUSPENDED"
//suspend fun myFunction() {
//    println("Before")
//
//    delay(1000)
//
//    println("After")
//}



fun myDelay2(timeMillis: Long, continuation: Continuation<Unit>): Any {
    val continuation = continuation as? MyFunctionContinuation ?: MyFunctionContinuation(continuation)

    if(continuation.label == 0) {
        continuation.label = 1
        executor.schedule({
            continuation.resume(Unit)
            println("delayed")
        }, timeMillis, TimeUnit.MILLISECONDS)
        println("returning suspension")
        return COROUTINE_SUSPENDED
    }

    if(continuation.label == 1) {
        println("myDelay2 finished")
        return Unit
    }

    error("Impossible")
}

fun myFunction(continuation: Continuation<Unit>): Any {
    val continuation = continuation as? MyFunctionContinuation ?: MyFunctionContinuation(continuation)

    if (continuation.label == 0) {
        println("Before")

        continuation.label = 1

        if (myDelay2(1000, continuation) == "COROUTINE_SUSPENDED") {
            return "COROUTINE_SUSPENDED"
        }

    }

    if (continuation.label == 1) {
        println("After")
        return Unit
    }
    error("Impossible")
}


class MyFunctionContinuation(private val completion: Continuation<Unit>) : Continuation<Unit> {
    override val context: CoroutineContext
        get() = completion.context

    var label: Int = 0
    private var result: Result<Any>? = null

    override fun resumeWith(result: Result<Unit>) {
        this.result = result
        val res = try {
            val r = myFunction(this)
            if (r == "COROUTINE_SUSPENDED") return
            Result.success(r as Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
        completion.resumeWith(res)
    }


}


class MyDelayContinuation(private val completion: Continuation<Unit>) : Continuation<Unit> {
    override val context: CoroutineContext
        get() = completion.context

    var label: Int = 0
    private var result: Result<Any>? = null

    override fun resumeWith(result: Result<Unit>) {
        this.result = result
        val res = try {
            val r = myDelay2(0, this)
            if (r == "COROUTINE_SUSPENDED") return
            Result.success(r as Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
        completion.resumeWith(res)
    }


}
