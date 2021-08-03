package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogPageViewSliderBinding

class DialogSelectWeekPattern(private val pattern: MutableList<Boolean>, private val listener: OnPatternSelected) : DialogFragment(), SelectableBubblesWeekLVA.OnElementPress {

    interface OnPatternSelected {
        fun onPatternSelected(pattern: MutableList<Boolean>)
    }

    lateinit var recyclerView: RecyclerView
    private lateinit var rvAdapter: SelectableBubblesWeekLVA

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogPageViewSliderBinding.inflate(LayoutInflater.from(context))

            recyclerView = binding.rvPatternLength
            rvAdapter = SelectableBubblesWeekLVA(this, requireContext())
            setupRV()

            builder.setView(binding.root)
                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    listener.onPatternSelected(pattern)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    this.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun setupRV() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false)
            adapter = rvAdapter
        }
        rvAdapter.submitList(pattern)
    }

    override fun onElementPress(position: Int) {
        pattern[position] = !pattern[position]
        rvAdapter.submitList(pattern)
    }

}