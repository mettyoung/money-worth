package com.mettyoung.moneyworth.common

data class RequestState<T>(
    val data: List<T> = listOf(),
    val loading: Boolean = false,
    val error: String? = null,
    val version: Int = (0..1000).random()
)