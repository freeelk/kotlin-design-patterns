package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування Стан (State)
 *
 * Це поведінковий шаблон, який дозволяє об'єкту змінювати свою поведінку залежно
 * від свого внутрішнього стану, ніби змінюється клас самого об'єкта. Він реалізує логіку
 * скінченного автомата шляхом створення окремих класів для кожного стану та передачі
 * виконання методів об'єкта відповідному об'єкту-стану.
 **************************************************************************************/

sealed class AuthorizationState

object Unauthorized: AuthorizationState()

class Authorized(val username: String): AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val isAuthorized: Boolean
        get() = when (state) {
            is Authorized -> true
            is Unauthorized -> false
        }

    val username: String
        get() {
            return when (val state = this.state) {
                is Authorized -> state.username
                is Unauthorized -> "Unknown"
            }
        }

    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }

    override fun toString(): String = "User $username"
}

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("admin")
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(true)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("admin")

        authorizationPresenter.logoutUser()
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(false)
        Assertions.assertThat(authorizationPresenter.username).isEqualTo("Unknown")
    }

}