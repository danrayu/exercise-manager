package com.example.exercisemanager.ui.muscles

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogMuscleEditorBinding

class MuscleEditorDialogueFragment(
    private var listener: EditMuscleDialogListener, private val muscle: Muscle,
    private val muscleIndex: Int) : DialogFragment() {

    interface EditMuscleDialogListener {
        fun onEditMuscleConfirm(muscle: Muscle)
        fun onMuscleDeleteClick(dialog: DialogFragment, muscleIndex: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogMuscleEditorBinding.inflate(LayoutInflater.from(context))
            binding.etEditMname.setText(muscle.name)
            builder.setView(binding.root)
                    .setPositiveButton(R.string.confirm_changes,
                            DialogInterface.OnClickListener { _, _ ->
                                muscle.name = binding.etEditMname.text.toString()
                                listener.onEditMuscleConfirm(muscle)
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })
            val btnDeleteMuscle: Button = binding.btnDeleteMuscle
            btnDeleteMuscle.setOnClickListener {
                listener.onMuscleDeleteClick(this, muscleIndex)
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}