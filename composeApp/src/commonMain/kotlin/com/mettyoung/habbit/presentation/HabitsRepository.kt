package com.mettyoung.habbit.presentation

import com.mettyoung.habbit.domain.Habit

interface HabitsRepository {

    suspend fun getHabits(forceFetch: Boolean): List<Habit>

    suspend fun addHabit(habit: Habit): Boolean
}
