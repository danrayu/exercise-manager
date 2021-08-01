package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentManageSchedulesBinding
import com.example.exercisemanager.src.DataBaseHandler
import org.threeten.bp.LocalDate

class ScheduleManagerFragment : Fragment(), ScheduleRVAdapter.OnEditSchedule {

    private lateinit var binding: FragmentManageSchedulesBinding
    private lateinit var db: DataBaseHandler
    private lateinit var schedules: MutableList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBaseHandler(requireContext())
        schedules = db.readScheduleData(db.readableDatabase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageSchedulesBinding.inflate(inflater)
        binding.rvManageSchedules.adapter = ScheduleRVAdapter(schedules, this)
        binding.rvManageSchedules.layoutManager = LinearLayoutManager(context)
        binding.btnAddSchedule.setOnClickListener {
            onEditInterface(Schedule(0, "", null, "", LocalDate.now()))
        }
        return binding.root
    }

    override fun onEditInterface(schedule: Schedule) {
        val fragment = ScheduleEditorFragment(schedule)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack("ScheduleEditorFragment")
            .commit()
    }
}