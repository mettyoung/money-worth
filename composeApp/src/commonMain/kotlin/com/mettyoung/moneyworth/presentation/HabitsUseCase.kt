package com.mettyoung.moneyworth.presentation

class HabitsUseCase(private val repo: HabitsRepository) : HabitsRepository by HabitsRepositoryImpl() {

}
