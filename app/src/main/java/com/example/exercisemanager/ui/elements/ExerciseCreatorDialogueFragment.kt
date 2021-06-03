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
import com.example.exercisemanager.ui.muscles.Muscle
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.dialog_ex_creator.view.*

class ExerciseCreatorDialogueFragment( private val listener: CreateExerciseDialogListener,
                                       private var muscleList: MutableList<Muscle>)
    : DialogFragment(), ExerciseMuscleRVAdapter.UnpickEventInterface {

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: ExerciseMuscleRVAdapter
    private lateinit var spinnerArrayAdapter: ArrayAdapter<String>
    private val exu = ExerciseManagerUtility()

    interface CreateExerciseDialogListener {
        fun onCreateExerciseClick(name: String, desc: String, muscles: MutableList<Muscle>)
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
            val view = inflater.inflate(R.layout.dialog_ex_creator, null)
            view.rv_pick_muscles.layoutManager = LinearLayoutManager(context)
            rvAdapter = ExerciseMuscleRVAdapter(selectedMusclesList, this)
            view.rv_pick_muscles.adapter = rvAdapter
            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton(R.string.create_exercise,
                            DialogInterface.OnClickListener { _, _ ->
                                val eName = view.et_enter_ename.text.toString()
                                val eDesc = view.et_enter_edescription.text.toString()
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
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                                val len = selectedMusclesList.size
                                selectedMusclesList.clear()
                                rvAdapter.notifyItemRangeRemoved(0, len)
                            })

            val searchableSpinner: SearchableSpinner = view.findViewById(R.id.ss_select_muscle_create)
            searchableSpinner.adapter = spinnerArrayAdapter
            val btnSelectMuscle: Button = view.findViewById(R.id.btn_select_muscle)
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