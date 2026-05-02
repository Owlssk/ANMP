package com.nmp.anmp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitListFragment : Fragment() {
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var habitAdapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvHabits = view.findViewById<RecyclerView>(R.id.rvHabits)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        habitAdapter = HabitAdapter(emptyList()) { id, delta ->
            viewModel.updateProgress(id, delta)
        }

        rvHabits.layoutManager = LinearLayoutManager(context)
        rvHabits.adapter = habitAdapter

        viewModel.habitList.observe(viewLifecycleOwner) { habits ->
            habitAdapter.updateData(habits)
        }
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_create)
        }
        viewModel.refreshData()
    }
}