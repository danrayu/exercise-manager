package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayoutHome
        TabLayoutMediator(tabLayout, binding.pagerHome) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }
}