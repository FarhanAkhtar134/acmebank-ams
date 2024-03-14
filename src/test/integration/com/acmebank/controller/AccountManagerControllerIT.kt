package com.acmebank.controller

import com.acmebank.dto.AccountDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountManagerControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun shouldReturnAccountBalance() {

        val accountId = "12345678"
        val balance = 1000000.0

        val accountDTO = AccountDTO(accountId, balance)

        val result = webTestClient.get()
            .uri("/v1/accounts/$accountId/balance")
            .exchange()
            .expectStatus().isOk
            .expectBody(AccountDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(accountDTO, result)
    }


}