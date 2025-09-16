package org.example

import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування "Замісник" (Proxy)
 *
 * Це структурний патерн проєктування, що дає змогу підставляти замість реальних
 * об’єктів спеціальні об’єкти-замінники. Ці об’єкти перехоплюють виклики до
 * оригінального об’єкта, дозволяючи зробити щось до чи після передачі виклику оригіналові.
 **************************************************************************************/

interface Image {
    fun display()
}

class RealImage(private val filename: String): Image {
    init {
        loadFromDisk(filename)
    }

    override fun display() {
        println("RealImage: displaying $filename")
    }

    private fun loadFromDisk(filename: String) {
        println("RealImage: loading $filename")
    }
}

class ProxyImage(private val filename: String): Image {
    private var realImage: RealImage? = null

    override fun display() {
        println("ProxyImage: displaying $filename")

        if (realImage == null) {
            realImage = RealImage(filename)
        }

        realImage!!.display()
    }
}

class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage("test.jpg")

        // load image from disk
        image.display()
        println("-----------------------------")

        // load image from "cache"
        image.display()
    }
}