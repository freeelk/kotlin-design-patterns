package org.example

import org.junit.jupiter.api.Test

/*************************************************************************************
 * Патерн проєктування "Фабрика" (Factory Method)
 *
 * Патерни проєктування "Фабрика" (Factory Method) та "Абстрактна фабрика"
 * (Abstract Factory) — це два споріднені, але різні патерни.
 * Фабричний метод визначає інтерфейс для створення об'єктів у суперкласі,
 * дозволяючи підкласам змінювати їх тип.
 ************************************************************************************/
sealed class Country


data class Spain(val someProperty: String) : Country()
data class Greece(val someProperty: String) : Country()
data class USA(val someProperty: String) : Country()
class Currency(val code: String)
data class Canada(val someProperty: String) : Country()


object CurrencyFactory {
    fun currencyForFactory(country: Country): Currency =
        when (country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Canada -> Currency("CAD")
        }
}

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val spanishCurrency = CurrencyFactory.currencyForFactory(Spain("")).code
        println("Spanish currency:$spanishCurrency")

        val greekCurrency = CurrencyFactory.currencyForFactory(Greece("")).code
        println("Greek currency:$greekCurrency")

        val usaCurrency = CurrencyFactory.currencyForFactory(USA("")).code
        println("USA currency:$usaCurrency")

        val canadianCurrency = CurrencyFactory.currencyForFactory(Canada("")).code
        println("Canadian currency:$canadianCurrency")
    }
}