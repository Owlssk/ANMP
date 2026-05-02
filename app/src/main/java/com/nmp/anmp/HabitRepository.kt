package com.nmp.anmp

object HabitRepository {
    private val habits = mutableListOf<Habit>()
    private var nextId = 1
    fun getHabits(): List<Habit> = habits

    fun addHabit(name: String, desc: String, goal: Int, unit: String, icon: String) {
        habits.add(Habit(nextId++, name, desc, goal, 0, unit, icon))
    }
    fun updateProgress(id: Int, delta: Int) {
        val habit = habits.find { it.id == id }
        habit?.let {
            it.currentProgress = (it.currentProgress + delta).coerceIn(0, it.goal)
        }
    }
}