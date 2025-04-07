package part3

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() : Unit = coroutineScope {
    val channel = Channel<Int>()

    launch {
        repeat(5) { index ->
            delay(500)
            println("Producing next one $index")
            channel.send((1+index) * 2)
        }
    }


    launch {
        repeat(5) {
            val received = channel.receive()
            println("Received $received")
        }
    }
}