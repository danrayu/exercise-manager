package com.example.exercisemanager.ui.exercises

import android.app.Dialog
import android.content.DialogInterface
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
import com.example.exercisemanager.ui.home.TargetMusclesRVA
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ExerciseCreatorDialogueFragment(private val listener: CreateExerciseDialogListener,
                                      private var muscleList: MutableList<Muscle>,
                                      private val allNames: MutableList<String>)
    : DialogFragment(),  SearchableSpinnerDialog.OnElementPressed, TargetMusclesRVA.OnRemoveElement{

    var selectedMusclesList: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: TargetMusclesRVA
    private val exu = ExerciseManagerUtility()
    private var eDesc = ""

    interface CreateExerciseDialogListener {
        fun onCreateExerciseClick(name: String, desc: String, muscles: MutableList<Muscle>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogExCreatorBinding.inflate(LayoutInflater.from(context))

            val layoutManager = FlexboxLayoutManager(requireContext())
            binding.rvPickMuscles.layoutManager = layoutManager
            rvAdapter = TargetMusclesRVA(selectedMusclesList, this)
            layoutManager.flexWrap = FlexWrap.WRAP
            binding.rvPickMuscles.adapter = rvAdapter
            binding.etEnterEdescription.setText(eDesc)

            binding.btnSaveItem.setOnClickListener {
                val eName = binding.etEnterEname.text.toString()
                eDesc = binding.etEnterEdescription.text.toString()
                if (eName.isNotBlank()) {
                    if (eName !in allNames) {
                        listener.onCreateExerciseClick(eName, eDesc, selectedMusclesList)
                        selectedMusclesList.clear()
                        rvAdapter.notifyItemRangeRemoved(0, selectedMusclesList.size)
                        this.dismiss()
                    }
                    else {
                        Toast.makeText(context, "Name taken", Toast.LENGTH_SHORT).show()
                        builder.show()
                    }
                } else {
                    Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                    builder.show()
                }
            }

            builder.setView(binding.root).setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { _, _ ->
                    val len = selectedMusclesList.size
                    selectedMusclesList.clear()
                    rvAdapter.notifyItemRangeRemoved(0, len)
                    eDesc = ""
                    this.dismiss()
                })

            val spinnerDialog = SearchableSpinnerDialog(this, muscleList.toMutableList())
            binding.btnExCreatorAddMuscle.setOnClickListener {
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