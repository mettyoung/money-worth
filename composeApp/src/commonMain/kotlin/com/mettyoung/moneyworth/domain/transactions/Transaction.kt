package com.mettyoung.moneyworth.domain

data class Transaction(val _id: String?, var name: String, var totalCount: Int, var currentCount: Int = 0)