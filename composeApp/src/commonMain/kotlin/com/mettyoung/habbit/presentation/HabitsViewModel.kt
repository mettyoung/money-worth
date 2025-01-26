package com.mettyoung.habbit.presentation

import com.mettyoung.habbit.common.RequestState
import com.mettyoung.habbit.domain.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitsViewModel : BaseViewModel() {

    private val _state: MutableStateFlow<RequestState<Habit>> =
        MutableStateFlow(RequestState(loading = true))

    val state: StateFlow<RequestState<Habit>> get() = _state

    init {
        getHabits()
    }

    fun getHabits(forceFetch: Boolean = false) {
        scope.launch {
            _state.emit(RequestState(data = listOf(
                Habit("1", "habit one"),
                Habit("2", "habit two"),
                Habit("3", "habit three"),
                Habit("4", "habit four")
            )))
        }
    }
}