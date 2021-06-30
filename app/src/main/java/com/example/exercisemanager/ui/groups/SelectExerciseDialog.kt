package com.example.exercisemanager.ui.groups

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.elements.ExerciseMuscleRVAdapter
import com.example.exercisemanager.ui.exercises.Exercise
import kotlinx.android.synthetic.main.dialog_ex_creator.view.*

class SelectExerciseDialog(val exercisePool: MutableList<Exercise>) : DialogFragment() {

    private val exu = ExerciseManagerUtility()
    private lateinit var spinnerArrayAdapter: ArrayAdapter<String>

    interface onAddGroupExercise {
        fun addGroupExercise(exercise: Exercise)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val exerciseNameList: MutableList<String> = ArrayList()
        for (exercise in exercisePool) {
            exerciseNameList.add(exercise.name)
        }
        spinnerArrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            exerciseNameList
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_select_exercise, null)
            val builder = AlertDialog.Builder(it)
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(
                    R.string.select_exercise,
                    DialogInterface.OnClickListener { _, _ ->
                        val exercise = exu.connectStringToExercise(searchableSpinner.selectedItem.toString())
                        if (eName.isNotBlank()) {
                            listener.onCreateExerciseClick(eName, eDesc, selectedMusclesList)
                        }
                        else {
                            Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                        }
                        val len = selectedMusclesList.size
                        selectedMusclesList.clear()
                        rvAdapter.notifyItemRangeRemoved(0, len)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        this.dismiss()
                        val len = selectedMusclesList.size
                        selectedMusclesList.clear()
                        rvAdapter.notifyItemRangeRemoved(0, len)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}