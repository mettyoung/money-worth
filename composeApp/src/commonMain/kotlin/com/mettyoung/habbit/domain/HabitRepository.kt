package com.mettyoung.habbit.domain

interface HabitRepository {
    fun findAll(): List<Habit>
    fun save(habit: Habit): Any
}
