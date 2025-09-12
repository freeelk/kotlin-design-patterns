package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування "Абстрактна фабрика" (Abstract Factory)
 *
 * Патерни проєктування "Фабрика" (Factory Method) та "Абстрактна фабрика"
 * (Abstract Factory) — це два споріднені, але різні патерни.
 * Абстрактна фабрика дозволяє створювати сімейства взаємопов'язаних об'єктів
 * без прив'язки до конкретних класів цих об'єктів.
 ************************************************************************************/

interface DataSource

class DatabaseDataSource: DataSource
class NetworkDataSource: DataSource

abstract class DataSourceFactory {
  abstract fun makeDataSource():  DataSource

  companion object {
          inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
              when (T::class) {
                  DatabaseDataSource::class -> DatabaseFactory()
                  NetworkDataSource::class -> NetworkFactory()
                  else -> throw IllegalArgumentException()
              }
  }

}
class DatabaseFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}
class NetworkFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class AbstractFactoryTest {
    @Test
    fun abstractFactoryTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()
        println("Created datasource $dataSource")

        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)
    }
}

