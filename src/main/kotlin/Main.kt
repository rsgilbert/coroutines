import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
//
fun main2(args: Array<String>) {
    repeat(100_000) {
        thread {
            println("thread $it")
//            Thread.sleep(1L)
//            print(".")
        }
    }
}

fun main() = runBlocking {
    repeat(100_000) {
        launch {
            println("Coroutine $this")
//            delay(1000L)
//            print(".")
        }
    }
}