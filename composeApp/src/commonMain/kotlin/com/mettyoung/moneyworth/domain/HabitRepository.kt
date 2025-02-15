package com.mettyoung.moneyworth.domain

interface HabitRepository {
    fun findAll(): List<Habit>
    fun save(habit: Habit): Any
}
