package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogPageViewSliderBinding

class DialogPatternLength(private val listener: OnLengthSelected) : DialogFragment() {

    interface OnLengthSelected {
        fun onLengthSelected(length: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogPageViewSliderBinding.inflate(LayoutInflater.from(context))
            binding.vpLengthSlider.adapter = PatternLengthVPAdapter()

            builder.setView(binding.root)
                .setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        this.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}