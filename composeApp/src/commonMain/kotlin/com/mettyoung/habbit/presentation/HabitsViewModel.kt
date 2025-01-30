package com.mettyoung.habbit.presentation

import com.mettyoung.habbit.common.RequestState
import com.mettyoung.habbit.domain.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val useCase: HabitsUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RequestState<Habit>> =
        MutableStateFlow(RequestState(loading = true))

    val state: StateFlow<RequestState<Habit>> get() = _state

    init {
        getHabits()
    }

    fun getHabits(forceFetch: Boolean = false) {
        scope.launch {
            _state.emit(RequestState(loading = true, data = _state.value.data))

            val fetchedHabits = useCase.getHabits(forceFetch)

            _state.emit(RequestState(data = fetchedHabits))
        }
    }

    fun addHabit(habit: Habit): Boolean = scope.launch {
        useCase.addHabit(habit)
    }.isCompleted

    fun deleteHabit(index: Int) : Boolean = scope.launch {
        useCase.deleteHabit(index)
    }.isCompleted

    fun checkHabit(habit: Habit) : Boolean = scope.launch {
        useCase.checkHabit(habit)
    }.isCompleted
}