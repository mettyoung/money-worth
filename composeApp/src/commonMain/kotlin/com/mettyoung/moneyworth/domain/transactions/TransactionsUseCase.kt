package com.mettyoung.moneyworth.presentation

class TransactionsUseCase(private val repo: TransactionsRepository) :
    TransactionsRepository by TransactionsRepositoryImpl() {

}
