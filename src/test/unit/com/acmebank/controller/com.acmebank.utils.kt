package com.acmebank.controller

import com.acmebank.dto.AccountDTO

fun createAccountDTO(id: String = "12345678", balance: Double = 1000000.0) = AccountDTO(id,balance)