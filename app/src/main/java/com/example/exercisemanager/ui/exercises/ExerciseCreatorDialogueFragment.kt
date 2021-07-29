package com.example.exercisemanager.ui.exercises

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogExCreatorBinding
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog

class ExerciseCreatorDialogueFragment(private val listener: CreateExerciseDialogListener,
                                      private var muscleList: MutableList<Muscle>)
    : DialogFragment(), ExerciseMuscleRVAdapter.UnpickEventInterface, SearchableSpinnerDialog.OnElementPressed {

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: ExerciseMuscleRVAdapter
    private val exu = ExerciseManagerUtility()
    private var eDesc = ""

    interface CreateExerciseDialogListener {
        fun onCreateExerciseClick(name: String, desc: String, muscles: MutableList<Muscle>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogExCreatorBinding.inflate(LayoutInflater.from(context))

            binding.rvPickMuscles.layoutManager = LinearLayoutManager(context)
            rvAdapter = ExerciseMuscleRVAdapter(selectedMusclesList, this)
            binding.rvPickMuscles.adapter = rvAdapter
            binding.etEnterEdescription.setText(eDesc)
            builder.setView(binding.root)
                    // Add action buttons
                    .setPositiveButton(R.string.create_exercise
                    ) { _, _ ->
                        val eName = binding.etEnterEname.text.toString()
                        eDesc = binding.etEnterEdescription.text.toString()
                        if (eName.isNotBlank()) {
                            listener.onCreateExerciseClick(eName, eDesc, selectedMusclesList)
                            selectedMusclesList.clear()
                            rvAdapter.notifyItemRangeRemoved(0, selectedMusclesList.size)
                        } else {
                            Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                            builder.show()
                        }

                    }
                .setNegativeButton(R.string.cancel
                ) { _, _ ->
                    this.dismiss()
                    val len = selectedMusclesList.size
                    selectedMusclesList.clear()
                    rvAdapter.notifyItemRangeRemoved(0, len)
                    eDesc = ""
                }
            val spinnerDialog = SearchableSpinnerDialog(this, muscleList.toMutableList())
            binding.btnExCreatorAddMuscle.setOnClickListener {
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