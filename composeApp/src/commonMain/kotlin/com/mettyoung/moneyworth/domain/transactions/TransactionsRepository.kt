package com.mettyoung.moneyworth.presentation

import com.mettyoung.moneyworth.domain.Transaction

interface TransactionsRepository {

    suspend fun getTransactions(forceFetch: Boolean): List<Transaction>

    suspend fun addTransaction(transaction: Transaction): Boolean

    suspend fun deleteTransaction(index: Int): Boolean

    suspend fun checkTransaction(transaction: Transaction): Boolean
}
