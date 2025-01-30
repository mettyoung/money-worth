package com.mettyoung.habbit.presentation

import com.mettyoung.habbit.domain.Habit

class HabitsRepositoryImpl : HabitsRepository {
    val habits: MutableList<Habit> = mutableListOf()

    override suspend fun getHabits(forceFetch: Boolean): List<Habit> {
        return habits
    }

    override suspend fun addHabit(habit: Habit) = habits.add(habit)

    override suspend fun deleteHabit(index: Int): Boolean = habits.removeAt(index).let { true }

    override suspend fun checkHabit(habit: Habit): Boolean = habits.find { it == habit }
        ?.let { it.currentCount++ } != null
}
