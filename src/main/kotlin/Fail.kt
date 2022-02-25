import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    try {
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        failedConcurrentSum()
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            println("async1 runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("async2 runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}