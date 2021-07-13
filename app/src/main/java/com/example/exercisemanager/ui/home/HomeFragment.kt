package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        var pageList : MutableList<String> = ArrayList()
        pageList.add("A")
        pageList.add("B")
        binding.pagerHome.adapter = HomePagerAdapter(pageList)
        return binding.root
    }
}