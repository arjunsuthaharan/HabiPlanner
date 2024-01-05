package com.example.habit_tracker.presentation

import androidx.lifecycle.ViewModel
import com.example.habit_tracker.data.HabitDAO

class HabitsViewModel(
    private val dao: HabitDAO
): ViewModel() {

}