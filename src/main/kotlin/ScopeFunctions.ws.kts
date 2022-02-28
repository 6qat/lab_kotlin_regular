import kotlin.random.Random

/**
 * https://kotlinlang.org/docs/scope-functions.html
 * this: run(), with() and apply()
 * it: let() and also()
 *
 * return the object context: apply() and also()
 * return the lambda result: let(), run() and with()
 */
data class Person(val name: String, var age: Int = 0, var city: String = "")

val str = "Hello"

// public inline fun <T, R> T.run(
//    block: T.() -> R
//): R
str.run {
    println("The string's length: $length")
}

// public inline fun <T, R> T.let(
//    block: (T) -> R
//): R
str.let {
    println("The string's length is ${it.length}")
}

// public inline fun <T> T.apply(
//    block: T.() -> Unit
//): T
val adam = Person("Adam").apply {
    age = 20
    city = "London"
}
adam

// public inline fun <T> T.also(
//    block: (T) -> Unit
//): T
Random.nextInt(100).also {
    println("getRandomInt() generated value $it")
}
Random.nextInt(100).also { value ->
    println("getRandomInt() generated value $value")
}

// APPLY() , ALSO() : return the context object
val numberList = mutableListOf<Double>()
numberList
    .also {
        println("Populating the list")
    }.apply {
        add(2.71)
        add(3.14)
        add(1.0)
    }.also {
        println("Sorting the list")
    }.sort()

// LET(), RUN(), WITH() : return the lambda result
val numbers = mutableListOf("one", "two", "three")
val countEndsWithE = numbers
    // public inline fun <T, R> T.run(
    //     block: T.() -> R
    // ): R
    .run {
        this.add("four")
        this.add("five")
        this.count {
            it.endsWith("e")
        }
    }
println("There are $countEndsWithE elements that end with e.")

// public inline fun <T, R> with(
//    receiver: T,
//    block: T.() -> R
// ): R
with(numbers) {
    val firstItem = this.first()
    val lastItem = this.last()
    println("First item: $firstItem, last item: $lastItem")
}

var str2: String? = "Hello"
fun processNonNullString(i: String) = Unit
//processNonNullString(str2)       // compilation error: str can be null
val length = str2?.let {
    println("let() called on $it")
    processNonNullString(it)      // OK: 'it' is not null inside '?.let { }'
    it.length
}
length

listOf("one", "two", "three", "four")
    .first()
    .let { firstItem ->
        println("The first item of the list is '$firstItem'")
        if (firstItem.length >= 5) firstItem else "!$firstItem!"
    }
    .uppercase()

val numbers2 = mutableListOf("one", "two", "three")
with(numbers2) {
    println("'with' is called with argument $this")
    println("It contains $size elements")
}
val firstAndLast = with(numbers) {
    "The first element is ${first()}," + " the last element is ${last()}"
}
firstAndLast

// ** NON-EXTENSION FUNCTION RUN()
// *** Removing the run() call, we get a function instead of a value (like a lazy value)
val hexNumberRegex = run {
    val digits = "0-9"
    val hexDigits = "A-Fa-f"
    val sign = "+-"

    Regex("[$sign]?[$digits$hexDigits]+")
}
for (match in hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) {
    println(match.value)
}

// ############################################################################
// takeIf() and takeUnless()

val number = Random.nextInt(100)

val evenOrNull = number.takeIf { it % 2 == 0 }
val oddOrNull = number.takeUnless { it % 2 == 0 }
evenOrNull
oddOrNull

fun displaySubstringPosition(input: String, sub: String): Unit {
    input.indexOf(sub).takeIf { it >= 0 }?.let {
        println("The substring $sub is found in $input.")
        println("Its start position is $it.")
    }
}
displaySubstringPosition("010000011", "11")
//displaySubstringPosition("010000011", "12")

