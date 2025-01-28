package com.mettyoung.habbit.features.habits

import com.mettyoung.habbit.presentation.HabitsRepository
import com.mettyoung.habbit.presentation.HabitsRepositoryImpl
import com.mettyoung.habbit.presentation.HabitsUseCase
import com.mettyoung.habbit.presentation.HabitsViewModel
import org.koin.dsl.module

val habitsModule = module {
    single<HabitsViewModel> { HabitsViewModel(get()) }
    single<HabitsUseCase> { HabitsUseCase(get()) }
    single<HabitsRepository> { HabitsRepositoryImpl() }
}