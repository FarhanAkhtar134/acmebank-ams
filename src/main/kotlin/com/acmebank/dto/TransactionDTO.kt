package com.acmebank.dto

import com.acmebank.entity.TransactionStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class TransactionDTO(
    @field:NotBlank(message = "TransactionDTO.fromAccount must not be blank")
    val fromAccount: String,
    @field: NotBlank(message = "TransactionDTO.toAccount must not be blank")
    val toAccount: String,
    @field:Positive(message = "Amount must be greater than zero")
    @field:Max(value = 1000000, message = "Amount must be less than or equal to 1,000,000")
    val amount: Double,
    @field:Schema(readOnly = true)
    val status : TransactionStatus?
)