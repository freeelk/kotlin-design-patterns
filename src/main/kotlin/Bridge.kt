package org.example

import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування "Міст" (Bridge)
 *
 * Структурний шаблон проєктування, який розділяє одну класову ієрархію на дві незалежні:
 * абстракцію та реалізацію, дозволяючи їм змінюватись окремо одна від одної.
 * Цей патерн корисний для створення кросплатформенних додатків або для роботи з
 * різними системами, як-от бази даних або хмарні сервіси, шляхом відділення логіки
 * керування від конкретних деталей реалізації.
 * Патерн "Міст" відокремлює інтерфейс (абстракцію) від його конкретного втілення (реалізації),
 * замість того, щоб зв'язувати їх назавжди через наслідування.
 * Абстракція та реалізація можуть розвиватися та змінюватися паралельно та незалежно,
 * що робить код більш гнучким та розширюваним.
 * Абстракція делегує виконання певних завдань об'єкта реалізації, який може бути
 * замінений на інший об'єкт реалізації без зміни основної логіки абстракції.
 **************************************************************************************/

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio: Device {
    override var volume = 0
    override fun getName() = "Radio $this"
}

class TV: Device {
    override var volume = 0
    override fun getName() = "TV $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(val device: Device): Remote {
    override fun volumeUp() {
        device.volume ++
        println("${device.getName()} volume up: ${device.volume}")
    }

    override fun volumeDown() {
        device.volume --
        println("${device.getName()} volume down: ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = TV()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

    }
}