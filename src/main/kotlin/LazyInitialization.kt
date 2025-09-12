package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування відкладеної (лінивої) ініціалізації (Lazy Initialization)
 *
 * Це техніка, за якої створення об'єкта або обчислення значення відкладається до моменту,
 * коли цей результат дійсно потрібен. Замість того, щоб одразу виділяти ресурси
 * на дорогі операції, ініціалізація відбувається "на вимогу", коли до об'єкта чи
 * значення надходить перший запит на доступ, що дозволяє заощадити час і ресурси,
 * якщо об'єкт так і не буде використаний.
 **************************************************************************************/
class AlertBox {
    var message: String? = null

    fun show() {
        println("Alert box $this: $message")
    }
}

class Window {
    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class WindowTest() {
    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Hello ower there!")
        Assertions.assertThat(window.box).isNotNull

        val window2 = Window2()
        //println(window2.box)
        window2.showMessage("Second window!")
        Assertions.assertThat(window2.box).isNotNull
    }
}