package com.example.exercisemanager.ui.elements

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.ui.muscles.Muscle
import kotlinx.android.synthetic.main.dialog_ex_editor.view.*
import kotlinx.android.synthetic.main.dialog_muscle_editor.view.*

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
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_muscle_editor, null)
            view.et_edit_mname.setText(muscle.name)
            builder.setView(view)
                    .setPositiveButton(R.string.confirm_changes,
                            DialogInterface.OnClickListener { _, _ ->
                                muscle.name = view.et_edit_mname.text.toString()
                                listener.onEditMuscleConfirm(muscle)
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { _, _ ->
                                this.dismiss()
                            })
            val btnDeleteMuscle: Button = view.findViewById(R.id.btn_delete_muscle)
            btnDeleteMuscle.setOnClickListener {
                listener.onMuscleDeleteClick(this, muscleIndex)
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}