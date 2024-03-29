package com.example.habit_tracker.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.habit_tracker.data.Habit

data class HabitState(
    val habits: List<Habit> = emptyList(),
    val habitTitle: MutableState<String> = mutableStateOf(""),
    val habitDescription: MutableState<String> = mutableStateOf("")
)