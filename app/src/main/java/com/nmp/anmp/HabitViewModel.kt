package com.nmp.anmp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository

    val habitList: LiveData<List<Habit>>

    init {
        val habitDao = AppDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        habitList = repository.allHabits
    }

    fun updateProgress(id: Int, delta: Int) {
        viewModelScope.launch {
            repository.updateProgress(id, delta)
        }
    }

    fun addHabit(name: String, desc: String, goal: Int, unit: String, icon: String) {
        viewModelScope.launch {
            repository.addHabit(name, desc, goal, unit, icon)
        }
    }
}