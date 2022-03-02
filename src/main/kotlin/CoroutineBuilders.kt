import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

// runBlocking creates a scope while suspending main does not if we don’t use the coroutineScope function
// https://kt.academy/article/cc-builders
suspend fun main() {
    println("Starting main() function. Thread name ${Thread.currentThread().name}")
    g1()
//    g2()
    delay(3000)
}

fun g1() {
    GlobalScope.launch {
        delay(1000L)
        println("World! Coroutine: $coroutineContext, Thread ${Thread.currentThread().name}")
    }
    GlobalScope.launch {
        delay(1000L)
        println("World! Coroutine: $coroutineContext, Thread ${Thread.currentThread().name}")
    }
    GlobalScope.launch {
        delay(1000L)
        println("World! Coroutine: $coroutineContext, Thread ${Thread.currentThread().name}")
    }
    println("Hello, Thread ${Thread.currentThread().name}")
    //Thread.sleep(2000L)

}

fun g2() {
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        println("World! Thread ${Thread.currentThread().name}")
    }
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        println("World! Thread ${Thread.currentThread().name}")
    }
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        println("World! Thread ${Thread.currentThread().name}")
    }
    println("Hello, Thread ${Thread.currentThread().name}")
    //Thread.sleep(2000L)
}

