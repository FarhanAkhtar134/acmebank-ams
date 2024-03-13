package com.acmebank.repository

import com.acmebank.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountManagerRepository : CrudRepository<Account,String> {
}