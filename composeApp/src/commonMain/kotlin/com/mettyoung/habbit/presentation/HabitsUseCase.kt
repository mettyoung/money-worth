package com.mettyoung.habbit.presentation

class HabitsUseCase(private val repo: HabitsRepository) : HabitsRepository by HabitsRepositoryImpl() {

}
