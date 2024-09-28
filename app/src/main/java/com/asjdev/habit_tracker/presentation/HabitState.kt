package com.asjdev.habit_tracker.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.asjdev.habit_tracker.data.Habit

//Habit State data class
data class HabitState(
    val habits: List<Habit> = emptyList(),
    val habitStartDate: MutableState<Long> = mutableLongStateOf(0),
    val habitID: MutableState<Int> = mutableIntStateOf(0),
    val habitTitle: MutableState<String> = mutableStateOf(""),
    val habitDescription: MutableState<String> = mutableStateOf("")
)