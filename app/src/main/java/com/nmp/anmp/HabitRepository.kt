package com.nmp.anmp

import androidx.lifecycle.LiveData

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun addHabit(name: String, desc: String, goal: Int, unit: String, icon: String) {
        val habit = Habit(
            name = name,
            description = desc,
            goal = goal,
            currentProgress = 0,
            unit = unit,
            icon = icon
        )
        habitDao.insertHabit(habit)
    }

    suspend fun updateProgress(id: Int, delta: Int) {
        val habit = habitDao.getHabitById(id) ?: return
        habit.currentProgress = (habit.currentProgress + delta).coerceIn(0, habit.goal)
        habitDao.updateHabit(habit)
    }

    suspend fun getHabitById(id: Int): Habit? {
        return habitDao.getHabitById(id)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }
}