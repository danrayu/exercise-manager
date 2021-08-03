package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogCalendarBinding
import org.threeten.bp.LocalDate

class DialogSelectReferenceDate(private val listener: OnDateSelected) : DialogFragment() {

    interface OnDateSelected {
        fun onDateSelected(date: LocalDate)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogCalendarBinding.inflate(LayoutInflater.from(context))
            val calendar = binding.calDialog
            var date = LocalDate.now()
            calendar.date = System.currentTimeMillis()
            calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val fixedMonth = month + 1
                val stringYear = "$year-"
                val stringMonth = if (fixedMonth < 10) "0$fixedMonth-" else "$fixedMonth-"
                val stringDay = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                val dateString = stringYear + stringMonth + stringDay
                date = LocalDate.parse(dateString)
            }
            builder.setView(binding.root)
                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    listener.onDateSelected(date)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    this.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}