import kotlinx.coroutines.*

// https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html


fun main(): Unit = runBlocking {
    launch {
        // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}, $coroutineContext")
        delay(1000)
        println("main runBlocking      : After delay in thread ${Thread.currentThread().name}, $coroutineContext")
    }
    /**
     * The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread,
     * but only until the first suspension point. After suspension it resumes the coroutine in
     * the thread that is fully determined by the suspending function that was invoked.
     */
    launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}, $coroutineContext")
        delay(500)
        // The unconfined one resumes in the default executor thread that the delay function is using(kotlinx.coroutines.DefaultExecutor)
        println("Unconfined            : After delay in thread ${Thread.currentThread().name}, $coroutineContext")
    }
    launch(Dispatchers.Default) {
        // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}, $coroutineContext")
    }
    launch(newSingleThreadContext("MyOwnThread")) {
        // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}, $coroutineContext")
    }
    val stop = 0
}

