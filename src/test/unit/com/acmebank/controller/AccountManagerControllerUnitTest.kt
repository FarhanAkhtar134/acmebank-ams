package com.acmebank.controller

import com.acmebank.dto.AccountDTO
import com.acmebank.dto.TransactionDTO
import com.acmebank.entity.TransactionStatus
import com.acmebank.exception.ResourceNotFoundException
import com.acmebank.service.AccountManagerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [AccountManagerController::class])
@AutoConfigureWebTestClient
class AccountManagerControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var accountManagerService: AccountManagerService

    @Test
    fun shouldReturnAccountBalance() {

        val accountId = "12345678"
        val balance = 1000000.0

        every { accountManagerService.getAccountBalance(any()) } returns createAccountDTO()

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
    fun shouldReturnResourceNotFoundGivenNonExistentAccountID() {

        val accountId = "55555555"

        val errorMessage = "No Account found with the id $accountId"

        every { accountManagerService.getAccountBalance(any()) } throws ResourceNotFoundException(errorMessage)

        val response = webTestClient.get()
            .uri("/v1/accounts/$accountId/balance")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertNotNull(response)

    }


    @Test
    fun shouldTransferAmountBetweenAccounts() {
        val senderAccountId = "12345678"
        val receiverAccountId = "88888888"
        val amount = 1000.0

        val transaction = TransactionDTO(senderAccountId, receiverAccountId, amount, TransactionStatus.SUCCESSFUL)

        every { accountManagerService.transferAmount(any()) } returns transaction

        val result = webTestClient.put()
            .uri("/v1/accounts/transfer")
            .bodyValue(transaction)
            .exchange()
            .expectStatus().isOk
            .expectBody(TransactionDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(transaction,result)
    }



}