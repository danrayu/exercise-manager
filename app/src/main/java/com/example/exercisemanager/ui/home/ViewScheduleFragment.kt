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
import org.threeten.bp.LocalDate


class ViewScheduleFragment : Fragment(), DialogSortFilter.NotifyCategoriesApplied {

    private lateinit var binding: FragmentViewScheduleBinding
    private lateinit var db: DataBaseHandler
    private lateinit var elementList: MutableList<DisplayableItem>
    private lateinit var rvAdapter: UneditableGroupsExercisesRVAdapter
    private lateinit var date: LocalDate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        date = LocalDate.now()
        db = DataBaseHandler(context)
        elementList = db.readScheduledItemsAtDate(db, date)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewScheduleBinding.inflate(inflater)
        binding.rvTodaySchedule.layoutManager = LinearLayoutManager(context)
        rvAdapter = UneditableGroupsExercisesRVAdapter(elementList, requireContext())
        binding.rvTodaySchedule.adapter = rvAdapter

        binding.btnOpenSortFilterMenu.setOnClickListener {
            DialogSortFilter(this).show(childFragmentManager, null)
        }

        return binding.root
    }

    override fun onApplyCategoriesPressed(categories: Categories) {
        date = categories.date
        elementList = db.readScheduledItemsAtDate(db, date)
        elementList = categories.applyCategories(elementList)
        rvAdapter = UneditableGroupsExercisesRVAdapter(elementList, requireContext())
        binding.rvTodaySchedule.adapter = rvAdapter
    }
}