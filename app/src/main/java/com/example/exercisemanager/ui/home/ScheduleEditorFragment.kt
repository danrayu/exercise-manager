package com.example.exercisemanager.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.FragmentScheduleEditorBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import org.threeten.bp.LocalDate

const val DAYS_IN_WEEK = 7

class ScheduleEditorFragment(private var schedule: Schedule, private val callback: NotifyManager) : Fragment(),
    DialogPatternLength.OnLengthSelected, DialogSelectPattern.OnPatternSelected,
    DialogSelectWeekPattern.OnPatternSelected, DialogSelectReferenceDate.OnDateSelected,
    SearchableSpinnerDialog.OnElementPressed {

    lateinit var binding: FragmentScheduleEditorBinding
    private var pattern: MutableList<Boolean> = ArrayList()
    private lateinit var rvAdapter: UneditableGroupsExercisesRVAdapter
    private lateinit var db: DataBaseHandler
    private lateinit var allUnsortedItems: MutableList<DisplayableItem>
    val originalSchedule = schedule

    interface NotifyManager {
        fun onDeleteSchedule(schedule: Schedule)
        fun onCreateSchedule(schedule: Schedule)
        fun onUpdateSchedule(schedule: Schedule)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleEditorBinding.inflate(inflater)
        binding.tvDateDisplay.text = schedule.referenceDate.toString()
        rvAdapter = UneditableGroupsExercisesRVAdapter(schedule.displayableItems!!, requireContext())
        binding.rvScheduleEditorDisplay.adapter = rvAdapter
        binding.rvScheduleEditorDisplay.layoutManager = LinearLayoutManager(requireContext())
        allUnsortedItems = (db.readExercisesData(db.readableDatabase) +
                db.readGroupData(db.readableDatabase)).toMutableList()

        if (schedule.id == -1) {
            binding.tvEditScheduleHeading.text = "Add schedule"
        }
        else {
            binding.tvEditScheduleHeading.text = "Edit schedule"
        }

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
            com.example.exercisemanager.R.array.pattern_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        binding.tvPatternLengthDisplay.text = pattern.size.toString()
        binding.llPatternLengthInteractable.setOnClickListener {
            DialogPatternLength(this).show(childFragmentManager, null)
        }

        binding.btnSaveSchedule.setOnClickListener {
            if (schedule.id == -1) {
                db.insertSchedule(db.writableDatabase, schedule)
                val scheduleIdList = db.readScheduleIds(db.readableDatabase)
                schedule.id = scheduleIdList[scheduleIdList.size - 1]
                callback.onCreateSchedule(schedule)
            }
            else db.updateScheduleData(db.writableDatabase, schedule)
            callback.onUpdateSchedule(schedule)
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

        binding.btnDeleteSchedule.setOnClickListener {
            if (schedule.id == -1) {
                Toast.makeText(context, "Schedule doesn't exist yet", Toast.LENGTH_SHORT).show()
            }
            else {
                db.deleteScheduleData(db.writableDatabase, schedule.id)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                callback.onDeleteSchedule(originalSchedule)
                requireActivity().onBackPressed()
            }
        }

        binding.llDateSelect.setOnClickListener {
            DialogSelectReferenceDate(this).show(childFragmentManager, null)
        }

        binding.clAddItem.setOnClickListener {
            val spinnerDialog = SearchableSpinnerDialog(this, allUnsortedItems)
            spinnerDialog.show(childFragmentManager, null)
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
        binding.tvDateDisplay.text = schedule.referenceDate.toString()
    }

    override fun elementPressedInRV(item: DisplayableItem) {
        schedule.displayableItems!!.add(item)
        rvAdapter.notifyItemInserted(schedule.displayableItems!!.size - 1)
    }
}