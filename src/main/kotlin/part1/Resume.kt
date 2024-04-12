package part1

import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main1() {
    suspendCoroutine<Unit> {  }

    val i: Int = suspendCoroutine<Int> { cont ->
        cont.resume(42)
    }
    println(i)

    val str: String = suspendCoroutine {
        it.resume("Hello")
    }
    println(str)

    val b: Boolean = suspendCoroutine {
        it.resume(true)
    }
    println(b)
}

suspend fun main() {
    println("Start")

    val user: User = requestUser()
    println(user)

    println("End")
}

suspend fun requestUser() : User {
    delay(1000)
    return suspendCancellableCoroutine <User> {
        it.resume(User("Jane", "Eyre"))
    }
}


data class User(
    val firstName: String,
    val lastName: String
)