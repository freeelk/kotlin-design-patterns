package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування "Адаптер" (Adapter)
 *
 * Структурний шаблон проєктування, призначений для організації використання функцій
 * об'єкта, недоступного для модифікації, через спеціально створений інтерфейс.
 * Адаптує інтерфейс одного класу в інший, очікуваний клієнтом.
 * Адаптер забезпечує роботу класів з несумісними інтерфейсами, та найчастіше
 * застосовується тоді, коли система підтримує необхідні дані й поведінку,
 * але має невідповідний інтерфейс.
 * Адаптер передбачає створення класу-оболонки з необхідним інтерфейсом.
 **************************************************************************************/

// Third party functionality

data class DisplayDataType(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

// Our code

data class DatabaseData(val position: Int, val amount: Int)

class DataBaseDataGenerator {
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(2, 3))
        list.add(DatabaseData(3, 7))
        list.add(DatabaseData(4, 23))
        return list
    }
}

interface DataBaseDataConvertor {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay): DataBaseDataConvertor {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        for (datum in data) {
            val ddt = DisplayDataType(datum.position.toFloat(), datum.amount.toString())
            display.displayData(ddt)
            returnList.add(ddt)
        }
        return returnList
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val generator = DataBaseDataGenerator()
        val generatedData = generator.generateData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertData = adapter.convertData(generatedData)

        Assertions.assertThat(convertData.size).isEqualTo(3)
        Assertions.assertThat(convertData[1].index).isEqualTo(3f)
        Assertions.assertThat(convertData[1].data).isEqualTo("7")
    }
}