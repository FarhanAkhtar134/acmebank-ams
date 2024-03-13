package com.acmebank.service

import com.acmebank.dto.AccountDTO
import com.acmebank.exception.ResourceNotFoundException
import com.acmebank.repository.AccountManagerRepository
import org.springframework.stereotype.Service

@Service
class AccountManagerService(val accountManagerRepository: AccountManagerRepository) {

    fun getAccountBalance(accountId: String): AccountDTO {
        val account = accountManagerRepository.findById(accountId)
        return if (account.isPresent) {
            account.get().let {
                AccountDTO(it.id, it.balance)
            }
        } else {
            throw ResourceNotFoundException("No Account found with the id $accountId")
        }

    }

}