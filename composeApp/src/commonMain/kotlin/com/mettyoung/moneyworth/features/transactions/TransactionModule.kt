package com.mettyoung.moneyworth.features.transactions

import com.mettyoung.moneyworth.presentation.TransactionsRepository
import com.mettyoung.moneyworth.presentation.TransactionsRepositoryImpl
import com.mettyoung.moneyworth.presentation.TransactionsUseCase
import com.mettyoung.moneyworth.presentation.TransactionsViewModel
import org.koin.dsl.module

val transactionsModule = module {
    single<TransactionsViewModel> { TransactionsViewModel(get()) }
    single<TransactionsUseCase> { TransactionsUseCase(get()) }
    single<TransactionsRepository> { TransactionsRepositoryImpl() }
}