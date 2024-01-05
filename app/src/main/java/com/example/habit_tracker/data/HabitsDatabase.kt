package com.example.habit_tracker.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Habit::class],
    version = 1
)
abstract class HabitsDatabase: RoomDatabase() {
    abstract val dao: HabitDAO
}