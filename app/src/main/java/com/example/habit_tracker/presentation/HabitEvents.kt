package com.example.habit_tracker.presentation

import com.example.habit_tracker.data.Habit

sealed interface HabitEvents{
    object SortHabits: HabitEvents
    data class DeleteHabit(val habit: Habit): HabitEvents
    data class SaveHabit(
        val habitTitle: String,
        val habitDescription: String
    ) : HabitEvents
    data class UpdateHabit(
        val habitID: Int,
        val habitTitle: String,
        val habitDescription: String
    ) : HabitEvents

}
