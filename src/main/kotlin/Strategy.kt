package org.example

import org.junit.jupiter.api.Test
import java.util.Locale.getDefault

/*************************************************************************************
 * Патерн проєктування Стратегія (Strategy)
 *
 * Патерн проєктування Strategy (Стратегія) — це поведінковий шаблон, який дозволяє
 * визначати сімейство схожих алгоритмів, інкапсулювати кожен з них в окремий клас і
 * робити їх взаємозамінними. Це дає можливість обирати потрібний алгоритм під час
 * виконання програми, не змінюючи сам клас, який цей алгоритм використовує.
 **************************************************************************************/

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String) {
        println(stringFormatterStrategy(message))
    }
}

val lowercaseFormatter = {it: String -> it.lowercase(getDefault()) }
val upperFormatter = {it: String -> it.uppercase(getDefault()) }

class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowercasePrinter = Printer(lowercaseFormatter)
        val uppercasePrinter = Printer(upperFormatter)
        uppercasePrinter.printString(inputString)
        lowercasePrinter.printString(inputString)
    }
}