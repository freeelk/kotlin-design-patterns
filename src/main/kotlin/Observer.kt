package org.example

import java.io.File
import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування Observer (Спостерігач)
 *
 * Це поведінковий патерн, який встановлює залежність "один-до-багатьох" між об'єктами,
 * дозволяючи їм вільно відстежувати зміни стану іншого об'єкта (видавця/subject) та
 * автоматично реагувати на них. Коли стан видавця змінюється, він автоматично надсилає
 * повідомлення всім об'єктам-спостерігачам, які підписані на нього, що дозволяє
 * підтримувати синхронізацію даних і реалізацію механізмів підписки та сповіщень у програмі.
 **************************************************************************************/

interface EventListener {
    fun update(eventType: String?, file: File?)
}

class EventManager(vararg operations: String) {
    var listeners = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for (operation: String in operations) {
            listeners[operation] = ArrayList()
        }
    }

    fun subscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }

    fun unsubscribe(eventType: String?, listener: EventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }

    fun notify(eventType: String?, file: File?) {
        val users = listeners[eventType]
        users?.let {
            for (listener in it) {
                listener.update(eventType, file)
            }
        }
    }
}

class Editor {
    var events = EventManager("open", "save")
    private var file: File? = null

    fun openFile(filePath: String) {
        file = File(filePath)
        events.notify("open", file)
    }

    fun saveFile() {
        file?.let {
            events.notify("save", file)
        }
    }
}

class EmailNotificationListener(private val email: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Email to $email: Someone has performed $eventType operation with the file ${file?.name}")
    }
}

class LogOpenListener(var filename: String): EventListener {
    override fun update(eventType: String?, file: File?) {
        println(
            "Save to log $filename: Someone has performed $eventType operation with the file ${file?.name}"
        )
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()
        val logEventListener = LogOpenListener("path/to/log/file.txt")
        val emailNotificationListener = EmailNotificationListener("test@test.com")

        editor.events.subscribe("open", logEventListener )
        editor.events.subscribe("open", emailNotificationListener )
        editor.events.subscribe("save", emailNotificationListener )

        editor.openFile("test.txt")
        editor.saveFile()
        editor.events.unsubscribe("open", emailNotificationListener )
        editor.openFile("test1.txt")
    }

}