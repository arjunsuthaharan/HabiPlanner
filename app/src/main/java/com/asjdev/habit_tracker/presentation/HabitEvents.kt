package com.asjdev.habit_tracker.presentation

import com.asjdev.habit_tracker.data.Habit

// HabitEvents interface
sealed interface HabitEvents{
    object SortHabits: HabitEvents
    data class DeleteHabit(val habit: Habit): HabitEvents
    data class SaveHabit(
        val habitTitle: String,
        val habitDescription: String
    ) : HabitEvents
    data class UpdateHabit(
        val habitID: Int,
        val habitStartDate: Long,
        val habitTitle: String,
        val habitDescription: String
    ) : HabitEvents

}
