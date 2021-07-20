package com.example.exercisemanager.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.FragmentViewScheduleBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.src.Scheduler


class ViewScheduleFragment : Fragment() {

    private lateinit var binding: FragmentViewScheduleBinding

    private lateinit var db: DataBaseHandler

    private lateinit var elementList: MutableList<DisplayableItem>
    private lateinit var rvAdapter: ViewScheduleFragmentRVAdapter
    private lateinit var scheduler: Scheduler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        scheduler  = Scheduler(db)
        elementList = scheduler.showScheduledItemsToday()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewScheduleBinding.inflate(inflater)
        binding.rvTodaySchedule.layoutManager = LinearLayoutManager(context)
        rvAdapter = ViewScheduleFragmentRVAdapter(elementList, requireContext())
        binding.rvTodaySchedule.adapter = rvAdapter

        return binding.root
    }
}