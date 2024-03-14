package com.acmebank.service

import com.acmebank.dto.AccountDTO
import com.acmebank.dto.TransactionDTO
import com.acmebank.entity.Account
import com.acmebank.entity.TransactionStatus
import com.acmebank.exception.InvalidAccountException
import com.acmebank.exception.InvalidTransferAmountException
import com.acmebank.exception.ResourceNotFoundException
import com.acmebank.repository.AccountManagerRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

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

    @Transactional
    fun transferAmount(transactionDTO: TransactionDTO): TransactionDTO {
        val senderAccount = accountManagerRepository.findById(transactionDTO.fromAccount)
        val receiverAccount = accountManagerRepository.findById(transactionDTO.toAccount)

        return if (isTransferValid(senderAccount, receiverAccount, transactionDTO)) {
            performTransaction(senderAccount, receiverAccount, transactionDTO)
        } else {
            handleTransferFailure(senderAccount, receiverAccount)
        }

    }

    private fun handleTransferFailure(
        senderAccount: Optional<Account>,
        receiverAccount: Optional<Account>
    ): Nothing {
        if (senderAccount.isEmpty || receiverAccount.isEmpty) {
            throw InvalidAccountException("Invalid account numbers provided")
        } else {
            throw InvalidTransferAmountException("Invalid transfer amount")
        }
    }

    private fun performTransaction(
        senderAccount: Optional<Account>,
        receiverAccount: Optional<Account>,
        transactionDTO: TransactionDTO
    ): TransactionDTO {
        val fromAccount = senderAccount.get()
        val toAccount = receiverAccount.get()
        fromAccount.balance -= transactionDTO.amount
        toAccount.balance += transactionDTO.amount
        accountManagerRepository.save(fromAccount)
        accountManagerRepository.save(toAccount)
        return TransactionDTO(
            transactionDTO.fromAccount,
            transactionDTO.toAccount,
            transactionDTO.amount,
            TransactionStatus.SUCCESSFUL
        )
    }

    private fun isTransferValid(
        senderAccount: Optional<Account>,
        receiverAccount: Optional<Account>,
        transactionDTO: TransactionDTO
    ) = senderAccount.isPresent && receiverAccount.isPresent && senderAccount.get().balance >= transactionDTO.amount

}