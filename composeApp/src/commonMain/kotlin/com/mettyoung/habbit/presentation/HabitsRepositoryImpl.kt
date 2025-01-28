package com.mettyoung.habbit.presentation

import com.mettyoung.habbit.domain.Habit

class HabitsRepositoryImpl : HabitsRepository {
    val habits: MutableList<Habit> = mutableListOf()

    override suspend fun getHabits(forceFetch: Boolean): List<Habit> {
        return habits
    }

    override suspend fun addHabit(habit: Habit) = habits.add(habit)
}
