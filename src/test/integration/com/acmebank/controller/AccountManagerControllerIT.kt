package com.acmebank.controller

import com.acmebank.dto.AccountDTO
import com.acmebank.dto.TransactionDTO
import com.acmebank.entity.Account
import com.acmebank.entity.TransactionStatus
import com.acmebank.repository.AccountManagerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AccountManagerControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var accountManagerRepository: AccountManagerRepository


    @BeforeEach
    fun setUp() {
        val account1Id = Account("12345678", 1000000.0)
        val account2Id = Account("88888888", 1000000.0)

        accountManagerRepository.save(account1Id)
        accountManagerRepository.save(account2Id)
    }

    @AfterEach
    fun cleanup() {
        accountManagerRepository.deleteAll()
    }

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

    @Test
    fun shouldTransferAmountBetweenAccounts() {
        val senderAccountId = "12345678"
        val receiverAccountId = "88888888"
        val amount = 1000.0

        val transaction = TransactionDTO(senderAccountId, receiverAccountId, amount, status = null)

        val result = webTestClient.put()
            .uri("/v1/accounts/transfer")
            .bodyValue(transaction)
            .exchange()
            .expectStatus().isOk
            .expectBody(TransactionDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(TransactionStatus.SUCCESSFUL, result!!.status)
    }
}