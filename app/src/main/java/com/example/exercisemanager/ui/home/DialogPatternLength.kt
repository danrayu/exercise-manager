package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
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
            val layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, true)
            binding.rvPatternLength.adapter = PatternLengthVPAdapter()
            val snapHelper = LinearSnapHelper()
            binding.rvPatternLength.layoutManager = layoutManager
            layoutManager.reverseLayout

            snapHelper.attachToRecyclerView(binding.rvPatternLength)

            builder.setView(binding.root)
                .setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onLengthSelected(layoutManager.
                        getPosition(snapHelper.findSnapView(layoutManager)!!) + 1)
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