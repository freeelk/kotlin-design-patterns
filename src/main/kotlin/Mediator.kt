package org.example

import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування "Посередник" (Mediator)
 *
 * Це поведінковий патерн, який дозволяє зменшити зв'язаність між багатьма об'єктами,
 * централізуючи їхню взаємодію в одному об'єкті-посереднику. Він позбавляє компоненти
 * системи необхідності прямого зв'язку один з одним, перенаправляючи запити через
 * посередника, що робить код чистішим, легшим для розуміння та підтримки.
 **************************************************************************************/

class ChatUser(private val mediator: Mediator, private val name: String) {
    fun send(msg: String) {
        println("$name: Sending message: $msg")
        mediator.sendMessage(msg, this)
    }

    fun receive(msg: String) {
        println("$name: Received message: $msg")
    }
}

class Mediator {
    private val users = arrayListOf<ChatUser>()

    fun sendMessage(msg: String, user: ChatUser) {
        users.filter { it != user}
            .forEach { it.receive(msg) }
    }

    fun addUser(user: ChatUser): Mediator = apply {users.add(user)}
}

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val alice = ChatUser(mediator, "Alice")
        val bob = ChatUser(mediator, "Bob")
        val carol = ChatUser(mediator, "Carol")

        mediator.addUser(alice)
            .addUser(bob)
            .addUser(carol)

        carol.send("Hi everyone!")
    }
}