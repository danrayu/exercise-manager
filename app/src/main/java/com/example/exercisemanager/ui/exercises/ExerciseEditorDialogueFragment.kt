package com.example.exercisemanager.ui.exercises

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogExEditorBinding
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.home.TargetMusclesRVA
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ExerciseEditorDialogueFragment(private var listener: EditExerciseDialogListener,
                                     private val exercise: Exercise,
                                     private val exerciseIndex: Int,
                                     private val muscleList: MutableList<Muscle>,
                                     private val allNames: MutableList<String>)
    : DialogFragment(), TargetMusclesRVA.OnRemoveElement, SearchableSpinnerDialog.OnElementPressed {

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: TargetMusclesRVA
    private val exu = ExerciseManagerUtility()
    private var eDesc = ""

    interface EditExerciseDialogListener {
        fun onEditExerciseConfirm(exercise: Exercise, exerciseIndex: Int)
        fun onExerciseDeleteClick(dialog: DialogFragment, exerciseIndex: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogExEditorBinding.inflate(LayoutInflater.from(context))

            selectedMusclesList = exercise.muscles
            val layoutManager = FlexboxLayoutManager(requireContext())
            binding.rvPickMusclesEdit.layoutManager = layoutManager
            layoutManager.flexWrap = FlexWrap.WRAP
            rvAdapter = TargetMusclesRVA(selectedMusclesList, this)
            binding.rvPickMusclesEdit.adapter = rvAdapter

            binding.etEditEname.setText(exercise.name)
            binding.etEditEdescription.setText(exercise.description)

            binding.btnCloseCreateDialog.setOnClickListener {
                listener.onExerciseDeleteClick(this, exerciseIndex)
            }

            binding.btnSaveItem.setOnClickListener {
                val eName = binding.etEditEname.text.toString()
                eDesc = binding.etEditEdescription.text.toString()
                exercise.name = eName
                exercise.description = eDesc
                if (eName.isNotBlank()) {
                    if (eName !in allNames) {
                        listener.onEditExerciseConfirm(exercise, exerciseIndex)
                        selectedMusclesList.clear()
                        rvAdapter.notifyItemRangeRemoved(0, selectedMusclesList.size)
                    }
                    else {
                        Toast.makeText(context, "Name taken", Toast.LENGTH_SHORT).show()
                        builder.show()
                    }
                }
                else {
                    Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                    builder.show()
                }
            }
            builder.setView(binding.root)
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })

            val spinnerDialog = SearchableSpinnerDialog(this, muscleList.toMutableList())
            binding.btnExEditorAddMuscle.setOnClickListener {
                spinnerDialog.show(parentFragmentManager, null)
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
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

    override fun onRemoveElementCall(muscle: Muscle, muscleIndex: Int) {
        selectedMusclesList.removeAt(muscleIndex)
        rvAdapter.notifyItemRemoved(muscleIndex)
    }

}