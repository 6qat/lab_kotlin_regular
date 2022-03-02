import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun main() = g1()

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
    Thread.sleep(2000L)
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
    Thread.sleep(2000L)
}

