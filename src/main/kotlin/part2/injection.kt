package part2

import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

data class User(val id: String, val name: String)

abstract class UuidProviderContext: CoroutineContext.Element {
    abstract fun nextUuid(): String

    override val key: CoroutineContext.Key<*> = Key

    companion object Key: CoroutineContext.Key<UuidProviderContext>
}

class RealUuidProviderContext : UuidProviderContext() {
    override fun nextUuid(): String =
        UUID.randomUUID().toString()
}

class FakeUuidProviderContext(private val fakeUuid: String) : UuidProviderContext() {
    override fun nextUuid() = fakeUuid
}

suspend fun nextUuid(): String = checkNotNull(coroutineContext[UuidProviderContext]) {
    "UuidProviderContext not present"
}.nextUuid()

suspend fun makeUser(name: String) = User(id= nextUuid(), name=name)


suspend fun main() : Unit {
    // production case
    withContext(RealUuidProviderContext()) {
        println(makeUser("Gilbert"))
    }

    // test case
    withContext(FakeUuidProviderContext("TEST_UUID")) {
        println(makeUser("Test user"))
    }
}