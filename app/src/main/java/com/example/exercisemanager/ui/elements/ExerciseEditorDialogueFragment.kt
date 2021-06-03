package com.example.exercisemanager.ui.elements

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.muscles.Muscle
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.dialog_ex_creator.view.*
import kotlinx.android.synthetic.main.dialog_ex_editor.view.*

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
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_ex_editor, null)

            selectedMusclesList = exercise.muscles

            view.rv_pick_muscles_edit.layoutManager = LinearLayoutManager(context)
            rvAdapter = ExerciseMuscleRVAdapter(selectedMusclesList, this)
            view.rv_pick_muscles_edit.adapter = rvAdapter

            view.et_edit_ename.setText(exercise.name)
            view.et_edit_edescription.setText(exercise.description)

            builder.setView(view)
                    .setPositiveButton(R.string.confirm_changes,
                            DialogInterface.OnClickListener { _, _ ->
                                exercise.name = view.et_edit_ename.text.toString()
                                exercise.description = view.et_edit_edescription.text.toString()
                                exercise.muscles = selectedMusclesList
                                listener.onEditExerciseConfirm(exercise)
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })

            val btnDeleteExercise: Button = view.findViewById(R.id.btn_delete_exercise)
            btnDeleteExercise.setOnClickListener {
                listener.onExerciseDeleteClick(this, exerciseIndex)
            }

            val searchableSpinner: SearchableSpinner = view.findViewById(R.id.ss_select_muscle_edit)
            searchableSpinner.adapter = spinnerArrayAdapter
            val btnSelectMuscle: Button = view.findViewById(R.id.btn_select_muscle_edit)
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