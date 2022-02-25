import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {

    val time = measureTimeMillis {

        info("runBlocking", coroutineContext)
        // launch a new coroutine and continue
        launch(CoroutineName("guiga")) {
            info("launch", coroutineContext)
            doWorld()
        }
        println("*Hello*") // main coroutine continues while a previous one is delayed
        repeat(100) { i ->// launch a lot of coroutines
            launch {
                //delay(50L)
                print("$i")
            }
        }
        f()
        g()
        doNewWorld()
        doDoubleWorld()

    }

    println("Time to complete main(): $time")
}

private suspend fun doWorld() {
    delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
    println("*World!*") // print after delay
}

private suspend fun doNewWorld() = coroutineScope {
    info("doNewWorld()", coroutineContext)
}

private suspend fun doDoubleWorld() = coroutineScope {
    launch {
        delay(2000L)
        info("World2", coroutineContext)
    }

    launch {
        delay(1000L)
        info("World1", coroutineContext)
    }

}

fun <Ctx> Ctx.f()
        where Ctx : CoroutineScope {
    info("f()", coroutineContext)
}

fun CoroutineScope.g() {
    info("g()", coroutineContext)
}

fun info(name: String, ctx: CoroutineContext) {
    println("$name. Job: ${ctx[Job]}, Context: $ctx")
}
