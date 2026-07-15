package com.nmp.anmp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nmp.anmp.databinding.FragmentEditHabitBinding

class EditHabitFragment : Fragment() {

    private lateinit var binding: FragmentEditHabitBinding
    private val viewModel: HabitViewModel by viewModels()

    private val form = HabitForm()
    private var habitId: Int = 0
    private var currentProgress: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitId = arguments?.getInt("habitId") ?: 0

        val actvIcon = binding.tilIcon.editText as AutoCompleteTextView
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            HabitIcons.names
        )
        actvIcon.setAdapter(adapter)

        // isi form dari tabel Habit (room)
        viewModel.selectedHabit.observe(viewLifecycleOwner) { habit ->
            if (habit == null) {
                Toast.makeText(context, "Habit tidak ditemukan", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
                return@observe
            }

            currentProgress = habit.currentProgress

            form.name = habit.name
            form.description = habit.description
            form.goal = habit.goal.toString()
            form.unit = habit.unit

            // two way binding: set variable -> semua EditText ikut terisi
            binding.form = form
            binding.executePendingBindings()

            actvIcon.setText(HabitIcons.toName(habit.icon), false)
        }

        viewModel.updateFinished.observe(viewLifecycleOwner) { done ->
            if (done == true) {
                Toast.makeText(context, "Habit berhasil diupdate", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        binding.btnSubmit.setOnClickListener { submit(actvIcon.text.toString().trim()) }

        viewModel.loadHabit(habitId)
    }

    private fun submit(iconName: String) {
        // nilai diambil dari form, bukan dari findViewById -> hasil two way binding
        val name = form.name.trim()
        val desc = form.description.trim()
        val goalText = form.goal.trim()
        val unit = form.unit.trim()

        binding.tilHabitName.error = null
        binding.tilShortDesc.error = null
        binding.tilGoal.error = null
        binding.tilUnit.error = null
        binding.tilIcon.error = null

        var isValid = true

        if (name.isEmpty()) {
            binding.tilHabitName.error = "Habit name harus diisi"
            isValid = false
        }

        if (desc.isEmpty()) {
            binding.tilShortDesc.error = "Short description harus diisi"
            isValid = false
        }

        val goal = goalText.toIntOrNull()

        if (goalText.isEmpty()) {
            binding.tilGoal.error = "Goal harus diisi"
            isValid = false
        } else if (goal == null || goal <= 0) {
            binding.tilGoal.error = "Goal harus berupa angka lebih dari 0"
            isValid = false
        }

        if (unit.isEmpty()) {
            binding.tilUnit.error = "Unit harus diisi"
            isValid = false
        }

        if (iconName.isEmpty()) {
            binding.tilIcon.error = "Icon harus dipilih"
            isValid = false
        }

        if (!isValid) return

        val updated = Habit(
            id = habitId,
            name = name,
            description = desc,
            goal = goal!!,
            currentProgress = currentProgress.coerceAtMost(goal),
            unit = unit,
            icon = HabitIcons.toEmoji(iconName)
        )

        viewModel.updateHabit(updated)
    }
}
