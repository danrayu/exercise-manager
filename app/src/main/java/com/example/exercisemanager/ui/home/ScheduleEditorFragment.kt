package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentScheduleEditorBinding
import org.threeten.bp.LocalDate

const val DAYS_IN_WEEK = 7

class ScheduleEditorFragment(private var schedule: Schedule) : Fragment(),
    DialogPatternLength.OnLengthSelected, DialogSelectPattern.OnPatternSelected,
    DialogSelectWeekPattern.OnPatternSelected, DialogSelectReferenceDate.OnDateSelected {

    lateinit var binding: FragmentScheduleEditorBinding
    private var pattern: MutableList<Boolean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleEditorBinding.inflate(inflater)
        binding.tvDateSelection.text = schedule.referenceDate.toString()
        for (day in schedule.schedulePattern.split("")) {
            if ("1" == day) {
                pattern.add(true)
            }
            else {
                pattern.add(false)
            }
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
        binding.tvPatternLengthDisplay.text = pattern.size.toString()
        binding.llPatternLengthInteractable.setOnClickListener {
            DialogPatternLength(this).show(childFragmentManager, null)
        }

        binding.llPatternSelect.setOnClickListener {
            if (spinner.selectedItem.toString() == "Pattern") {
                DialogSelectPattern(pattern, this).show(childFragmentManager, null)
            }
            else if (spinner.selectedItem.toString() == "Weekly") {
                pattern.fixLength(DAYS_IN_WEEK)
                DialogSelectWeekPattern(pattern, this).show(childFragmentManager, null)
            }
        }
        binding.llDateSelect.setOnClickListener {
            DialogSelectReferenceDate(this).show(childFragmentManager, null)
        }
        return binding.root
    }

    override fun onLengthSelected(length: Int) {
        binding.tvPatternLengthDisplay.text = length.toString()
        pattern.fixLength(length)
    }

    override fun onPatternSelected(pattern: MutableList<Boolean>) {
        var squashedPattern = ""
        for (day in pattern) {
            squashedPattern += if (day) "1" else "0"
        }
        schedule.schedulePattern = squashedPattern
    }

    private fun MutableList<Boolean>.fixLength(length: Int) {
        while (length > pattern.size) {
            this.add(false)
        }
        while (length < pattern.size) {
            this.removeAt(pattern.size - 1)
        }
    }

    override fun onDateSelected(date: LocalDate) {
        schedule.referenceDate = date
        binding.tvDateSelection.text = schedule.referenceDate.toString()
    }
}