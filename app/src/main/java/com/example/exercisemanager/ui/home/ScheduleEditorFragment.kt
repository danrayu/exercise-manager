package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentScheduleEditorBinding

class ScheduleEditorFragment(val schedule: Schedule) : Fragment(), DialogPatternLength.OnLengthSelected {

    lateinit var binding: FragmentScheduleEditorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScheduleEditorBinding.inflate(inflater)
        binding.llPatternLengthInteractable.setOnClickListener {
            DialogPatternLength(this).show(parentFragmentManager, null)
        }
        val spinner = binding.spPatternTypes
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.pattern_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        return binding.root
    }

    override fun onLengthSelected(length: Int) {
        binding.tvPatternLengthDisplay.text = length.toString()
    }
}