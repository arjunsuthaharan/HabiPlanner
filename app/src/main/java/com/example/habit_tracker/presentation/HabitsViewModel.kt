package com.example.habit_tracker.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habit_tracker.data.Habit
import com.example.habit_tracker.data.HabitDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
// View Model class between presentation screens and data classes
class HabitsViewModel(
    private val dao: HabitDAO
): ViewModel() {
    private val isSortedByStartDate = MutableStateFlow(true)

    private var habits = isSortedByStartDate.flatMapLatest { sort ->
        if(sort) {
            dao.sortHabitsByStartDate()
        } else {
            dao.sortHabitsByName()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(HabitState())

    val state = combine(_state, isSortedByStartDate, habits){ state, isSortedByStartDate, habits ->
        state.copy(
            habits = habits
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HabitState())
    fun onEvent(event: HabitEvents) {
        when (event) {
            is HabitEvents.DeleteHabit -> {
                viewModelScope.launch{
                    dao.deleteHabit(event.habit)
                }
            }
            is HabitEvents.SaveHabit -> {
                val habit = Habit(
                    habitTitle = state.value.habitTitle.value,
                    habitDescription = state.value.habitDescription.value,
                    habitStartDate = System.currentTimeMillis()
                )

                viewModelScope.launch{
                    dao.upsertHabit(habit)
                }
                _state.update{
                    it.copy(
                        habitTitle = mutableStateOf(""),
                        habitDescription = mutableStateOf("")
                    )
                }
            }
            HabitEvents.SortHabits -> {
                isSortedByStartDate.value = !isSortedByStartDate.value
            }

            is HabitEvents.UpdateHabit -> {
                val habit = Habit(
                    habitID = state.value.habitID.value,
                    habitTitle = state.value.habitTitle.value,
                    habitDescription = state.value.habitDescription.value,
                    habitStartDate = state.value.habitStartDate.value
                )

                viewModelScope.launch{
                    dao.updateHabit(habit)
                }
                _state.update{
                    it.copy(
                        habitTitle = mutableStateOf(""),
                        habitDescription = mutableStateOf("")
                    )
                }
            }
        }
    }
}