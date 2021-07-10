package com.example.exercisemanager.ui.elements

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogExCreatorBinding
import com.example.exercisemanager.databinding.DialogExEditorBinding
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.muscles.Muscle
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

class ExerciseEditorDialogueFragment(private var listener: EditExerciseDialogListener,
                                     private val exercise: Exercise,
                                     private val exerciseIndex: Int,
                                     private val muscleList: MutableList<Muscle>)
    : DialogFragment(), ExerciseMuscleRVAdapter.UnpickEventInterface {

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: ExerciseMuscleRVAdapter
    private lateinit var spinnerArrayAdapter: ArrayAdapter<String>
    private val exu = ExerciseManagerUtility()


    interface EditExerciseDialogListener {
        fun onEditExerciseConfirm(exercise: Exercise)
        fun onExerciseDeleteClick(dialog: DialogFragment, exerciseIndex: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val muscleNameList: MutableList<String> = ArrayList()
        for (muscle in muscleList) {
            muscleNameList.add(muscle.name)
        }
        spinnerArrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            muscleNameList
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogExEditorBinding.inflate(LayoutInflater.from(context))

            selectedMusclesList = exercise.muscles

            binding.rvPickMusclesEdit.layoutManager = LinearLayoutManager(context)
            rvAdapter = ExerciseMuscleRVAdapter(selectedMusclesList, this)
            binding.rvPickMusclesEdit.adapter = rvAdapter

            binding.etEditEname.setText(exercise.name)
            binding.etEditEdescription.setText(exercise.description)

            builder.setView(binding.root)
                    .setPositiveButton(R.string.confirm_changes,
                            DialogInterface.OnClickListener { _, _ ->
                                exercise.name = binding.etEditEname.text.toString()
                                exercise.description = binding.etEditEdescription.text.toString()
                                exercise.muscles = selectedMusclesList
                                listener.onEditExerciseConfirm(exercise)
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })

            val btnDeleteExercise: Button = binding.btnDeleteExercise
            btnDeleteExercise.setOnClickListener {
                listener.onExerciseDeleteClick(this, exerciseIndex)
            }

            val searchableSpinner: SearchableSpinner = binding.ssSelectMuscleEdit
            searchableSpinner.adapter = spinnerArrayAdapter
            val btnSelectMuscle: Button = binding.btnSelectMuscleEdit
            btnSelectMuscle.setOnClickListener {
                val muscle = exu.connectStringToMuscle(searchableSpinner.selectedItem.toString(), muscleList)
                if (!selectedMusclesList.contains(muscle)) {
                    selectedMusclesList.add(muscle)
                    rvAdapter.notifyItemInserted(selectedMusclesList.size - 1)
                }
                else {
                    Toast.makeText(context, "Exercise already selected", Toast.LENGTH_SHORT).show()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun unpickMuscleButtonPressed(muscleIndex: Int) {
        selectedMusclesList.removeAt(muscleIndex)
        rvAdapter.notifyItemRemoved(muscleIndex)
    }

}