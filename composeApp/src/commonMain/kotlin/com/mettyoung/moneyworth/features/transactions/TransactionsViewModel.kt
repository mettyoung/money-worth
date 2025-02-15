package com.mettyoung.moneyworth.presentation

import com.mettyoung.moneyworth.common.RequestState
import com.mettyoung.moneyworth.domain.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val useCase: TransactionsUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RequestState<Transaction>> =
        MutableStateFlow(RequestState(loading = true))

    val state: StateFlow<RequestState<Transaction>> get() = _state

    init {
        getTransactions()
    }

    fun getTransactions(forceFetch: Boolean = false) {
        scope.launch {
            _state.emit(RequestState(loading = true, data = _state.value.data))

            val fetchedTransactions = useCase.getTransactions(forceFetch)

            _state.emit(RequestState(data = fetchedTransactions))
        }
    }

    fun addTransaction(transaction: Transaction): Boolean = scope.launch {
        useCase.addTransaction(transaction)
    }.isCompleted

    fun deleteTransaction(index: Int): Boolean = scope.launch {
        useCase.deleteTransaction(index)
        getTransactions(true)
    }.isCompleted

    fun checkTransaction(transaction: Transaction): Boolean = scope.launch {
        useCase.checkTransaction(transaction)
    }.isCompleted
}