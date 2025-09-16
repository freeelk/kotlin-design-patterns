package org.example

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування Ланцюжок обов'язків (Chain of Responsibility)
 *
 * Це поведінковий патерн, який дозволяє передавати запити послідовно ланцюжком обробників,
 * де кожен обробник вирішує, чи може він обробити запит, і якщо ні, передає його далі.
 * Цей патерн допомагає уникнути жорсткої прив'язки відправника запиту
 * до конкретного отримувача, дозволяючи динамічно формувати ланцюг із різних
 * обробників для обробки запиту.
 **************************************************************************************/


interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(val token: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token"
        .let { next?.addHeader(it) ?: it }
}

class ContentTypeHeader(val contentType: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContentType: $contentType"
            .let { next?.addHeader(it) ?: it }
}

class BodyPayloadHeader(val body: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nBody: $body"
            .let { next?.addHeader(it) ?: it }
}

class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("123456")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyPayloadHeader = BodyPayloadHeader("{\"username\"} = \"john\"}" )

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloadHeader

        val messageWithAuthentification = authenticationHeader.addHeader("Headers with authentification")
        println( messageWithAuthentification )

        println("-------------------------------------------------------------")

        val messageWithoutAuthentification = contentTypeHeader.addHeader("Headers without authentification")
        println( messageWithoutAuthentification )

        Assertions.assertThat(messageWithAuthentification).isEqualTo(
            """
                Headers with authentification
                Authorization: 123456
                ContentType: json
                Body: {"username"} = "john"}
            """.trimIndent()
        )
    }
}