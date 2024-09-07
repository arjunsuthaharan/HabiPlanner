package com.example.habit_tracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


// Data Access Object interface for database
// Contains queries for upsert, updating, deleting, sorting by title and date
@Dao
interface HabitDAO {
    @Upsert
    suspend fun upsertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit ORDER BY habitStartDate")
    fun sortHabitsByStartDate(): Flow<List<Habit>>


    @Query("SELECT * FROM habit ORDER BY habitTitle ASC")
    fun sortHabitsByName(): Flow<List<Habit>>
}