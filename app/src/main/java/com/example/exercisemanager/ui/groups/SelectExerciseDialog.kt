package com.example.exercisemanager.ui.groups

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogSelectExerciseBinding
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.exercises.Exercise
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

class SelectExerciseDialog(private val listener: OnAddGroupExercise,
                           private val exercisePool: MutableList<Exercise>) : DialogFragment() {

    private val exu = ExerciseManagerUtility()
    lateinit var spinnerArrayAdapter: ArrayAdapter<String>

    interface OnAddGroupExercise {
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
            val binding = DialogSelectExerciseBinding.inflate(LayoutInflater.from(context))
            val builder = AlertDialog.Builder(it)
            val searchableSpinner: SearchableSpinner = binding.ssSelectExercise
            searchableSpinner.adapter = spinnerArrayAdapter
            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(
                    R.string.select_exercise,
                    DialogInterface.OnClickListener { _, _ ->
                        val exercise = exu.connectStringToExercise(searchableSpinner.selectedItem.toString(), exercisePool)
                        if (exercise.name != "None") {
                            listener.addGroupExercise(exercise)
                        }
                        else {
                            Toast.makeText(context, "None selected", Toast.LENGTH_SHORT).show()
                        }
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        this.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}