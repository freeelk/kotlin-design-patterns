package org.example

import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування Команда (Command)
 *
 * Це поведінковий патерн проєктування, який перетворює запити на об’єкти, дозволяючи
 * передавати їх як аргументи під час виклику методів, ставити запити в чергу, логувати їх,
 * а також підтримувати скасування операцій.
 **************************************************************************************/

interface Command {
    fun execute()
}

class OrderAddCommand(val id: Long): Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}

class OrderPayCommand(val id: Long): Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command)}

    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor()
            .addToQueue(OrderAddCommand(1L))
            .addToQueue(OrderAddCommand(2L))
            .addToQueue(OrderPayCommand(2L))
            .addToQueue(OrderPayCommand(1L))
            .processCommands()
    }
}