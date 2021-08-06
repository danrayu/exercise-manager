package com.example.exercisemanager.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.databinding.FragmentGuideBinding

class GuideFragment : Fragment() {

    private lateinit var binding: FragmentGuideBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideBinding.inflate(inflater)
        return binding.root
    }
}