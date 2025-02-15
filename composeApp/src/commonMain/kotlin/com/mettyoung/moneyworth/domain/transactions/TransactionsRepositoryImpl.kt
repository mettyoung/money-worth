package com.mettyoung.moneyworth.presentation

import com.mettyoung.moneyworth.domain.Transaction

class TransactionsRepositoryImpl : TransactionsRepository {
    val transactions: MutableList<Transaction> = mutableListOf()

    override suspend fun getTransactions(forceFetch: Boolean): List<Transaction> {
        return transactions
    }

    override suspend fun addTransaction(transaction: Transaction) = transactions.add(transaction)

    override suspend fun deleteTransaction(index: Int): Boolean = transactions.removeAt(index).let { true }

    override suspend fun checkTransaction(transaction: Transaction): Boolean = transactions.find { it == transaction }
        ?.let { it.currentCount++ } != null
}
