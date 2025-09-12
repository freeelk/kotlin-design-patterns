package org.example
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions


/*************************************************************************************
 * Патерн проєктування "Фасад" (Facade)
 *
 * Патерн проєктування "Фасад" (Facade) — це структурний патерн, який надає простий
 * інтерфейс до складної системи класів, бібліотеки або фреймворку. Він спрощує
 * взаємодію з системою, приховуючи її внутрішню складність та надаючи зручну
 * точку входу для клієнтського коду. Фасад делегує запити відповідним об'єктам
 * складної системи, зменшуючи залежності та підвищуючи зручність використання.
 **************************************************************************************/

class ComplexSystemStore (private val filePath: String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from the file $filePath")
        cache = HashMap()
    }

    fun store(key: String, value: String) {
        cache[key] = value
    }

    fun read(key: String) = cache[key] ?: ""

    fun commit() = println("Storing cached data to file $filePath")
}

data class User(val login: String)

class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/default.prefs")

    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

class FacadeTest {
    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("john")
        userRepo.save(user)

        val retrieveUser = userRepo.findFirst()

        Assertions.assertThat(retrieveUser.login).isEqualTo("john")
    }
}