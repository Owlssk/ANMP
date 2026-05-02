package com.nmp.anmp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitViewModel : ViewModel() {
    private val _habitList = MutableLiveData<List<Habit>>()
    val habitList: LiveData<List<Habit>> get() = _habitList
    fun refreshData() {
        _habitList.value = HabitRepository.getHabits()
    }
    fun updateProgress(id: Int, delta: Int) {
        HabitRepository.updateProgress(id, delta)
        refreshData()
    }
}