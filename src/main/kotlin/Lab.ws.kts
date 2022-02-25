class Rectangle(private val height: Int, private val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }
}

Rectangle(10,20).isSquare

