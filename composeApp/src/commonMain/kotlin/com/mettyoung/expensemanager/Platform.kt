package com.mettyoung.expensemanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform