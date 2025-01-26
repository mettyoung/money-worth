package com.mettyoung.habbit.common

data class RequestState<T> (
    val data: List<T> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)