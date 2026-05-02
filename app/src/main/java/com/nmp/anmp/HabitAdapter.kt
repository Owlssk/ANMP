package com.nmp.anmp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator

class HabitAdapter(
    private var habits: List<Habit>,
    private val onUpdateProgress: (Int, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIcon: TextView = view.findViewById(R.id.tvIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvBadge: TextView = view.findViewById(R.id.tvBadge)
        val tvProgressValue: TextView = view.findViewById(R.id.tvProgressValue)
        val progressBar: LinearProgressIndicator = view.findViewById(R.id.progressBar)
        val btnMinus: FrameLayout = view.findViewById(R.id.btnMinus)
        val btnPlus: FrameLayout = view.findViewById(R.id.btnPlus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        holder.tvIcon.text = habit.icon
        holder.tvTitle.text = habit.name
        holder.tvDescription.text = habit.description

        holder.progressBar.max = habit.goal
        holder.progressBar.progress = habit.currentProgress
        holder.tvProgressValue.text = "${habit.currentProgress} / ${habit.goal} ${habit.unit}"

        if (habit.isCompleted) {
            holder.tvBadge.text = "Completed"
            holder.tvBadge.setTextColor(Color.parseColor("#4CAF50")) // Hijau
        } else {
            holder.tvBadge.text = "In Progress"
            holder.tvBadge.setTextColor(Color.parseColor("#6200EE")) // Ungu
        }

        holder.btnPlus.setOnClickListener {
            onUpdateProgress(habit.id, 1)
        }

        holder.btnMinus.setOnClickListener {
            onUpdateProgress(habit.id, -1)
        }
    }

    override fun getItemCount(): Int = habits.size

    fun updateData(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}