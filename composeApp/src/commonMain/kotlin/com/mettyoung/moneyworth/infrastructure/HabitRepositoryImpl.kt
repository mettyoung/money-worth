package com.mettyoung.moneyworth.infrastructure

import com.mettyoung.moneyworth.domain.Habit
import com.mettyoung.moneyworth.domain.HabitRepository

data class HabitRepositoryImpl(val habits: MutableList<Habit> = mutableListOf()) : HabitRepository {

    override fun findAll(): List<Habit> {
        return habits
    }

    override fun save(habit: Habit): Any =
        habits.find { it._id == habit._id }?.let { it.name = habit.name } ?: habits.add(habit)
}

