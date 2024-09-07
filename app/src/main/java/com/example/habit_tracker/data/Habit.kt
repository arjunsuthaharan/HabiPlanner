package com.example.habit_tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// data class for Habits
@Entity
data class Habit(
    val habitTitle: String,
    val habitDescription: String,
    val habitStartDate: Long,
    /*
    val habitStreakDays: Int,
    val habitBreakCount: Int,
    val habitLastBreak: Long,
    */
    @PrimaryKey(autoGenerate = true)
    val habitID: Int = 0

)
