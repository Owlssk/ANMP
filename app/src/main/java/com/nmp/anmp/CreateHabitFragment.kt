package com.nmp.anmp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class CreateHabitFragment : Fragment(R.layout.fragment_create_habit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilHabitName = view.findViewById<TextInputLayout>(R.id.tilHabitName)
        val tilShortDesc = view.findViewById<TextInputLayout>(R.id.tilShortDesc)
        val tilGoal = view.findViewById<TextInputLayout>(R.id.tilGoal)
        val tilUnit = view.findViewById<TextInputLayout>(R.id.tilUnit)
        val tilIcon = view.findViewById<TextInputLayout>(R.id.tilIcon)
        val btnCreate = view.findViewById<MaterialButton>(R.id.btnCreate)

        val actvIcon = tilIcon.editText as AutoCompleteTextView

        val iconOptions = listOf(
            "Fitness",
            "Study",
            "Water",
            "Meditation",
            "Food",
            "Sleep"
        )

        val iconMap = mapOf(
            "Fitness" to "💪",
            "Study" to "📚",
            "Water" to "💧",
            "Meditation" to "🧘",
            "Food" to "🍎",
            "Sleep" to "😴"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            iconOptions
        )

        actvIcon.setAdapter(adapter)
        actvIcon.setText(iconOptions[0], false)

        btnCreate.setOnClickListener {
            val name = tilHabitName.editText?.text.toString().trim()
            val shortDesc = tilShortDesc.editText?.text.toString().trim()
            val goalText = tilGoal.editText?.text.toString().trim()
            val unit = tilUnit.editText?.text.toString().trim()
            val selectedIconName = actvIcon.text.toString().trim()

            tilHabitName.error = null
            tilShortDesc.error = null
            tilGoal.error = null
            tilUnit.error = null
            tilIcon.error = null

            var isValid = true

            if (name.isEmpty()) {
                tilHabitName.error = "Habit name harus diisi"
                isValid = false
            }

            if (shortDesc.isEmpty()) {
                tilShortDesc.error = "Short description harus diisi"
                isValid = false
            }

            if (goalText.isEmpty()) {
                tilGoal.error = "Goal harus diisi"
                isValid = false
            }

            val goal = goalText.toIntOrNull()

            if (goal == null || goal <= 0) {
                tilGoal.error = "Goal harus berupa angka lebih dari 0"
                isValid = false
            }

            if (unit.isEmpty()) {
                tilUnit.error = "Unit harus diisi"
                isValid = false
            }

            if (selectedIconName.isEmpty()) {
                tilIcon.error = "Icon harus dipilih"
                isValid = false
            }

            if (!isValid) return@setOnClickListener

            val icon = iconMap[selectedIconName] ?: "⭐"

            HabitRepository.addHabit(
                name = name,
                desc = shortDesc,
                goal = goal!!,
                unit = unit,
                icon = icon
            )

            Toast.makeText(context, "Habit berhasil dibuat", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}