import java.util.Scanner
import kotlin.math.sqrt


interface Shape {
    fun calculateArea(): Double
    fun calculatePerimeter(): Double
}


class Square(private val side: Double) : Shape {
    init {
        require(side > 0) { "Side cannot be negative" }
    }

    override fun calculateArea(): Double {
        return side * side
    }

    override fun calculatePerimeter(): Double {
        return 4 * side
    }
}

class Rectangle(private val width: Double, private val height: Double) : Shape {
    init {
        require(width > 0) { "Width cannot be negative" }
        require(height > 0) { "Height cannot be negative" }
    }

    override fun calculateArea(): Double {
        return width * height
    }

    override fun calculatePerimeter(): Double {
        return 2 * (width + height)
    }
}

class Circle(private val radius: Double) : Shape {
    init {
        require(radius > 0) { "Radius cannot be negative" }
    }

    override fun calculateArea(): Double {
        return Math.PI * radius * radius
    }

    override fun calculatePerimeter(): Double {  // renamed to calculatePerimeter to follow interface
        return 2 * Math.PI * radius
    }

    fun calculateCircumference(): Double {
        return calculatePerimeter()
    }
}

abstract class Triangle : Shape {
    abstract val sideA: Double
    abstract val sideB: Double
    abstract val sideC: Double

    init {
        require(sideA > 0 && sideB > 0 && sideC > 0) { "Sides cannot be negative" }
        require(sideA + sideB > sideC && sideA + sideC > sideB && sideB + sideC > sideA) { "These sides do not form a valid triangle" }
    }

    override fun calculatePerimeter(): Double {
        return sideA + sideB + sideC
    }
}

class EquilateralTriangle(private val side: Double) : Triangle() {
    override val sideA: Double = side
    override val sideB: Double = side
    override val sideC: Double = side

    init {
        require(side > 0) { "Side cannot be negative" }
    }

    override fun calculateArea(): Double {
        return (sqrt(3.0) / 4) * side * side
    }
}


class IsoscelesTriangle( private val equalSide: Double, private val base: Double) : Triangle() {
    override val sideA: Double = equalSide
    override val sideB: Double = equalSide
    override val sideC: Double = base

    init {
        require(equalSide > 0 && base > 0) { "Sides cannot be negative" }
        require(2 * equalSide > base) { "These sides do not form a valid triangle" }
    }

    override fun calculateArea(): Double {
        val height = sqrt(equalSide * equalSide - (base * base) / 4)
        return (base * height) / 2
    }
}


class ScaleneTriangle(override val sideA: Double, override val sideB: Double, override val sideC: Double) : Triangle() {
    override fun calculateArea(): Double {
        val s = (sideA + sideB + sideC) / 2
        return sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))
    }
}

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Which shape would you like to calculate?")
        println("1. Square")
        println("2. Rectangle")
        println("3. Circle")
        println("4. Triangle")
        println("0. Exit")
        print("Enter your choice: ")
        val choice = scanner.nextInt()

        if (choice == 0) {
            println("Exiting the program.")
            break
        }

        try {
            when (choice) {
                1 -> {
                    print("Enter the side of the square: ")
                    val side = scanner.nextDouble()
                    val square = Square(side)
                    calculateShape(scanner, square)
                }
                2 -> {
                    print("Enter the width of the rectangle: ")
                    val width = scanner.nextDouble()
                    print("Enter the height of the rectangle: ")
                    val height = scanner.nextDouble()
                    val rectangle = Rectangle(width, height)
                    calculateShape(scanner, rectangle)
                }
                3 -> {
                    print("Enter the radius of the circle: ")
                    val radius = scanner.nextDouble()
                    val circle = Circle(radius)
                    calculateShape(scanner, circle, isCircle = true)
                }
                4 -> {
                    println("Select the type of triangle:")
                    println("1. Equilateral")
                    println("2. Isosceles")
                    println("3. Scalene")
                    print("Enter your choice: ")
                    val triangleType = scanner.nextInt()

                    when (triangleType) {
                        1 -> {
                            print("Enter the side of the equilateral triangle: ")
                            val side = scanner.nextDouble()
                            val equilateralTriangle = EquilateralTriangle(side)
                            calculateShape(scanner, equilateralTriangle)
                        }
                        2 -> {
                            print("Enter the equal side of the isosceles triangle: ")
                            val equalSide = scanner.nextDouble()
                            print("Enter the base of the isosceles triangle: ")
                            val base = scanner.nextDouble()
                            val isoscelesTriangle = IsoscelesTriangle(equalSide, base)
                            calculateShape(scanner, isoscelesTriangle)
                        }
                        3 -> {
                            print("Enter the first side of the scalene triangle: ")
                            val sideA = scanner.nextDouble()
                            print("Enter the second side of the scalene triangle: ")
                            val sideB = scanner.nextDouble()
                            print("Enter the third side of the scalene triangle: ")
                            val sideC = scanner.nextDouble()
                            val scaleneTriangle = ScaleneTriangle(sideA, sideB, sideC)
                            calculateShape(scanner, scaleneTriangle)
                        }
                        else -> {
                            println("Invalid choice, please try again.")
                        }
                    }
                }
                else -> {
                    println("Invalid choice, please try again.")
                }
            }
        } catch (e: IllegalArgumentException) {
            println(e.message)
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        }
    }
}

fun calculateShape(scanner: Scanner, shape: Shape, isCircle: Boolean = false) {
    println("What would you like to calculate?")
    println("1. Area")
    if (isCircle) {
        println("2. Circumference")
    } else {
        println("2. Perimeter")
    }
    print("Enter your choice: ")
    val calculationChoice = scanner.nextInt()

    when (calculationChoice) {
        1 -> println("The area of the shape is: ${shape.calculateArea()}")
        2 -> if (isCircle) {
            println("The circumference of the circle is: ${(shape as Circle).calculateCircumference()}")
        } else {
            println("The perimeter of the shape is: ${shape.calculatePerimeter()}")
        }
        else -> println("Invalid choice, please try again.")
    }
}