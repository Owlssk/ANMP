package com.nmp.anmp

data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val goal: Int,
    var currentProgress: Int = 0,
    val unit: String,
    val icon: String
) {
    val isCompleted: Boolean get() = currentProgress >= goal
}