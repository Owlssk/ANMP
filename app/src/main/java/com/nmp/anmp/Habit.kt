package com.nmp.anmp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val goal: Int,
    var currentProgress: Int = 0,
    val unit: String,
    val icon: String
) {
    val isCompleted: Boolean get() = currentProgress >= goal
}