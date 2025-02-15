package com.mettyoung.moneyworth.features.habits

import com.mettyoung.moneyworth.presentation.HabitsRepository
import com.mettyoung.moneyworth.presentation.HabitsRepositoryImpl
import com.mettyoung.moneyworth.presentation.HabitsUseCase
import com.mettyoung.moneyworth.presentation.HabitsViewModel
import org.koin.dsl.module

val habitsModule = module {
    single<HabitsViewModel> { HabitsViewModel(get()) }
    single<HabitsUseCase> { HabitsUseCase(get()) }
    single<HabitsRepository> { HabitsRepositoryImpl() }
}