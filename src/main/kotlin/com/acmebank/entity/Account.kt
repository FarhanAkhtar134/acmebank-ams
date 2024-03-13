package com.acmebank.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Accounts")
data class Account(
    @Id
    val id: String?,
    var balance: Double

)