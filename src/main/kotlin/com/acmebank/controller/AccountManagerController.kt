package com.acmebank.controller

import com.acmebank.dto.AccountDTO
import com.acmebank.service.AccountManagerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/accounts")
class AccountManagerController(val accountManagerService: AccountManagerService) {

    @GetMapping("/{accountId}/balance")
    fun getAccountBalance(@PathVariable("accountId") accountId: String): AccountDTO {

        return accountManagerService.getAccountBalance(accountId)
    }
}