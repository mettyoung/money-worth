package com.mettyoung.habbit.infrastructure

import com.mettyoung.habbit.domain.Habit
import com.mettyoung.habbit.domain.HabitRepository

data class HabitRepositoryImpl(val habits: MutableList<Habit> = mutableListOf()) : HabitRepository {

    override fun findAll(): List<Habit> {
        return habits
    }

    override fun save(habit: Habit): Any =
        habits.find { it._id == habit._id }?.let { it.name = habit.name } ?: habits.add(habit)
}

