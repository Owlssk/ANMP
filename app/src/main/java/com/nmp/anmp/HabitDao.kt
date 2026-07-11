package com.nmp.anmp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit ORDER BY id ASC")
    fun getAllHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE id = :habitId LIMIT 1")
    suspend fun getHabitById(habitId: Int): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)
}