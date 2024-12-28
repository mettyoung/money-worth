package com.mettyoung.habbit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform