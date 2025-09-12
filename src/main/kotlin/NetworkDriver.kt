package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування Singleton (або Одинак)
 *
 * Це породжувальний патерн, який гарантує, що в класі буде створений лише один екземпляр,
 * і надає глобальну точку доступу до цього екземпляра.
 * Це корисно, коли потрібен єдиний об'єкт для координації дій у системі,
 * як-от логер або менеджер підключень до бази даних.
 *************************************************************************************/
object NetworkDriver {
    init {
        println("Initializing: {$this}")
    }

    fun log(): NetworkDriver = apply { println("Network driver: $this")}
}

class SingletonTest {
    @Test
    fun testSingleton() {
        println("Start")
        val networkDriver1 = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        Assertions.assertThat(networkDriver1).isSameAs(NetworkDriver)
        Assertions.assertThat(networkDriver2).isSameAs(NetworkDriver)
    }
}