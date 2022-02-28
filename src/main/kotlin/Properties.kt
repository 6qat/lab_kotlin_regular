class Rectangle(val height: Int, val width: Int) {

    val isSquare: Boolean
        get() {
            return height == width
        }
}

fun main() {

    val r = Rectangle(10, 20)
    println(r.isSquare)

}
