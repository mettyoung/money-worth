package com.mettyoung.habbit.features.habits

import com.mettyoung.habbit.presentation.HabitsViewModel
import org.koin.dsl.module

val habitsModule = module {
    single<HabitsViewModel> { HabitsViewModel() }
}