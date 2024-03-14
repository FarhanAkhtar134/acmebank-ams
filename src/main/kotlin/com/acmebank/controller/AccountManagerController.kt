package com.acmebank.controller

import com.acmebank.dto.AccountDTO
import com.acmebank.dto.TransactionDTO
import com.acmebank.service.AccountManagerService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/accounts")
@Validated
class AccountManagerController(val accountManagerService: AccountManagerService) {

    @GetMapping("/{accountId}/balance")
    fun getAccountBalance(@PathVariable("accountId") accountId: String): AccountDTO {

        return accountManagerService.getAccountBalance(accountId)
    }

    @PutMapping("/transfer")
    fun transferAmount(@RequestBody @Valid transactionDTO: TransactionDTO): TransactionDTO {
        return accountManagerService.transferAmount(transactionDTO)

    }
}