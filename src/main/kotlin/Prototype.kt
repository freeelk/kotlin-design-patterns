package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування "Прототип"
 *
 * Це породжувальний шаблон, який дозволяє створювати нові об'єкти шляхом копіювання
 * об'єкта-прототипу, що існує, не знаючи деталей реалізації його класу.
 * Це особливо корисно для копіювання складних об'єктів, коли створення нового об'єкта
 * з нуля є занадто затратним або складним, і забезпечує точне дублювання,
 * включаючи дані та структуру.
 ************************************************************************************/

abstract class Shape: Cloneable {
    var type: String? = null

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}
class Circle: Shape() {
    override fun draw() {
        println("Inside Circle::draw() method.")
    }

    init {
        type = "Circle"
    }
}

class Square: Shape() {
    override fun draw() {
        println("Inside Square::draw() method.")
    }

    init {
        type = "Square"
    }
}

class Rectangle: Shape() {
    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }

    init {
        type = "Rectangle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()

        shapeMap["circle"] = circle
        shapeMap["square"] = square
        shapeMap["rectangle"] = rectangle
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val clonedShapeCircle = ShapeCache.getShape("circle")
        val clonedShapeSquare = ShapeCache.getShape("square")
        val clonedShapeRectangle= ShapeCache.getShape("rectangle")

        clonedShapeCircle.draw()
        clonedShapeSquare.draw()
        clonedShapeRectangle.draw()

        Assertions.assertThat(clonedShapeCircle.type).isEqualTo("Circle")
        Assertions.assertThat(clonedShapeSquare.type).isEqualTo("Square")
        Assertions.assertThat(clonedShapeRectangle.type).isEqualTo("Rectangle")
    }
}