package com.mettyoung.moneyworth.presentation

import com.mettyoung.moneyworth.domain.Habit

interface HabitsRepository {

    suspend fun getHabits(forceFetch: Boolean): List<Habit>

    suspend fun addHabit(habit: Habit): Boolean

    suspend fun deleteHabit(index: Int): Boolean

    suspend fun checkHabit(habit: Habit): Boolean
}
