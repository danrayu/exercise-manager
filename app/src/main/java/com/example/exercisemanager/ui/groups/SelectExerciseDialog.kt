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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.dialog_ex_creator.view.*

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
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_select_exercise, null)
            val builder = AlertDialog.Builder(it)
            val searchableSpinner: SearchableSpinner = view.findViewById(R.id.ss_select_exercise)
            searchableSpinner.adapter = spinnerArrayAdapter
            builder.setView(view)
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