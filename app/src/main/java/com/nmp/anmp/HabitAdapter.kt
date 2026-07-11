package com.nmp.anmp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmp.anmp.databinding.ItemHabitBinding

interface OnHabitInteractionListener {
    fun onPlusClicked(id: Int)
    fun onMinusClicked(id: Int)
}

class HabitAdapter(
    private var habits: List<Habit>,
    private val onUpdateProgress: (Int, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(), OnHabitInteractionListener {

    override fun onPlusClicked(id: Int) = onUpdateProgress(id, 1)
    override fun onMinusClicked(id: Int) = onUpdateProgress(id, -1)

    class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.binding.habit = habit
        holder.binding.listener = this
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = habits.size

    fun updateData(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}