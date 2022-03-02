import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume

// https://kt.academy/article/cc-suspension
fun main() = f4()

fun f1() = runBlocking {
    println("Before")
    suspendCancellableCoroutine<Unit> {
        println("Before too")
//        it.resume(Unit)
        it.resumeWith(Result.success(Unit))
        println("$coroutineContext")
    }
    println("After")
}

fun f2() = runBlocking {
    println("Before, Thread ${Thread.currentThread().name}")
    suspendCancellableCoroutine<Unit> {
        thread {
            println("Suspended $coroutineContext, Thread ${Thread.currentThread().name}")
            Thread.sleep(1000)
            it.resume(Unit)
            println("Resumed")
        }
    }
    println("After, Thread ${Thread.currentThread().name}")
}

fun continueAfterSecondWastingThreads(continuation: CancellableContinuation<Unit>) {
    thread {
        Thread.sleep(1000)
        continuation.resume(Unit)
    }
}

fun f3() = runBlocking {
    println("Before")
    suspendCancellableCoroutine<Unit> {
        continueAfterSecondWastingThreads(it)
    }
    println("After")
}

private val executor =
    Executors.newSingleThreadScheduledExecutor {
        Thread(it, "My Single Thread Scheduler").apply { isDaemon = true }
    }

fun f4() = runBlocking {
    println("Before, Thread ${Thread.currentThread().name}")
    suspendCancellableCoroutine<Unit> {
        executor.schedule({
            println("Suspended $coroutineContext, Thread name: ${Thread.currentThread().name}")
            it.resume(Unit)
        }, 1000, TimeUnit.MILLISECONDS)
    }
    println("After, Thread ${Thread.currentThread().name}")
}

suspend fun myDelay(time: Long): Unit =
    suspendCancellableCoroutine {
        executor.schedule({
            it.resume(Unit)
        }, time, TimeUnit.MILLISECONDS)
    }

