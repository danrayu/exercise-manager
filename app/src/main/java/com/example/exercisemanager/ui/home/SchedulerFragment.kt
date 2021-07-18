package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.databinding.SchedulesFragmentBinding

class SchedulerFragment : Fragment() {

    private lateinit var binding: SchedulesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SchedulesFragmentBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }
}