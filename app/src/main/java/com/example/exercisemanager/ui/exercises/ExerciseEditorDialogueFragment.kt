package com.example.exercisemanager.ui.exercises

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
import com.example.exercisemanager.databinding.DialogExEditorBinding
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog

class ExerciseEditorDialogueFragment(private var listener: EditExerciseDialogListener,
                                     private val exercise: Exercise,
                                     private val exerciseIndex: Int,
                                     private val muscleList: MutableList<Muscle>)
    : DialogFragment(), ExerciseMuscleRVAdapter.UnpickEventInterface, SearchableSpinnerDialog.OnElementPressed {

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: ExerciseMuscleRVAdapter
    private lateinit var spinnerArrayAdapter: ArrayAdapter<String>
    private val exu = ExerciseManagerUtility()
    private var eDesc = ""

    interface EditExerciseDialogListener {
        fun onEditExerciseConfirm(exercise: Exercise)
        fun onExerciseDeleteClick(dialog: DialogFragment, exerciseIndex: Int)
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
                                val eName = binding.etEditEname.text.toString()
                                eDesc = binding.etEditEdescription.text.toString()
                                if (eName.isNotBlank()) {
                                    listener.onEditExerciseConfirm(exercise)
                                    selectedMusclesList.clear()
                                    rvAdapter.notifyItemRangeRemoved(0, selectedMusclesList.size)
                                }
                                else {
                                    Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                                    builder.show()
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })

            val btnDeleteExercise: Button = binding.btnDeleteExercise
            btnDeleteExercise.setOnClickListener {
                listener.onExerciseDeleteClick(this, exerciseIndex)
            }

            val spinnerDialog = SearchableSpinnerDialog(this, muscleList.toMutableList())
            binding.btnExEditorAddMuscle.setOnClickListener {
                spinnerDialog.show(parentFragmentManager, null)
            }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun unpickMuscleButtonPressed(muscleIndex: Int) {
        selectedMusclesList.removeAt(muscleIndex)
        rvAdapter.notifyItemRemoved(muscleIndex)
    }

    override fun elementPressedInRV(item: DisplayableItem) {
        val muscle = exu.connectStringToMuscle(item.name, muscleList)
        if (!selectedMusclesList.contains(muscle)) {
            selectedMusclesList.add(muscle)
            rvAdapter.notifyItemInserted(selectedMusclesList.size - 1)
        }
        else {
            Toast.makeText(context, "Exercise already selected", Toast.LENGTH_SHORT).show()
        }
    }

}