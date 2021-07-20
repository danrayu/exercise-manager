package com.example.exercisemanager.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.SchedulesTodayFragmentBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem


class TodayFragment : Fragment() {

    private lateinit var binding: SchedulesTodayFragmentBinding

    private lateinit var db: DataBaseHandler

    private lateinit var elementList: MutableList<DisplayableItem>
    private lateinit var rvAdapter: TodayFragmentRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        elementList = db.readGroupData(db.readableDatabase).toMutableList()
        elementList.addAll(db.readExercisesData(db.readableDatabase).toMutableList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SchedulesTodayFragmentBinding.inflate(inflater)
        binding.rvTodaySchedule.layoutManager = LinearLayoutManager(context)
        rvAdapter = TodayFragmentRVAdapter(elementList, requireContext())
        binding.rvTodaySchedule.adapter = rvAdapter

        return binding.root
    }
}