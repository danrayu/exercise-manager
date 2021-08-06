package com.example.exercisemanager.ui.muscles

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogMuscleCreatorBinding

class MuscleCreatorDialogueFragment(listener: CreateMuscleDialogListener, var muscleList: MutableList<String>) : DialogFragment() {

    var listener: CreateMuscleDialogListener = listener

    interface CreateMuscleDialogListener {
        fun onCreateMuscleClick(name: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogMuscleCreatorBinding.inflate(LayoutInflater.from(context))
            binding.btnSaveItem.setOnClickListener {
                var mName = binding.etEnterMname.text.toString()
                if (mName.isNotBlank()) {
                    var foundCopy = false
                    for (muscle in muscleList) {
                        if (muscle == mName) {
                            Toast.makeText(context, "Muscle already exists", Toast.LENGTH_SHORT).show()
                            foundCopy = true
                            break
                        }
                    }
                    if (!foundCopy) {
                        listener.onCreateMuscleClick(mName)
                    }
                }
                else {
                    Toast.makeText(context, "No name specified", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setView(binding.root)
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                this.dismiss()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}