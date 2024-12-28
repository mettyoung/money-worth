package com.mettyoung.habitrabbit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform