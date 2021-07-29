package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.databinding.FragmentScheduleEditorBinding

class ScheduleEditorFragment(val schedule: Schedule) : Fragment(), DialogPatternLength.OnLengthSelected {

    lateinit var binding: FragmentScheduleEditorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScheduleEditorBinding.inflate(layoutInflater)
        binding.llPatternLengthInteractable.setOnClickListener {
            DialogPatternLength(this).show(parentFragmentManager, null)
        }

        return binding.root

    }

    override fun onLengthSelected(length: Int) {
    }
}