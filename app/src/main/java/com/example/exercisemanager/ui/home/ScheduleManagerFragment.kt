package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentManageSchedulesBinding
import com.example.exercisemanager.src.DataBaseHandler
import org.threeten.bp.LocalDate
import kotlin.properties.Delegates

class ScheduleManagerFragment : Fragment(), ScheduleRVAdapter.OnEditSchedule, ScheduleEditorFragment.NotifyManager {

    private lateinit var binding: FragmentManageSchedulesBinding
    private lateinit var db: DataBaseHandler
    private lateinit var schedules: MutableList<Schedule>
    private lateinit var rvadapter: ScheduleRVAdapter

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
        rvadapter = ScheduleRVAdapter(schedules, this)
        binding.rvManageSchedules.adapter = rvadapter
        binding.rvManageSchedules.layoutManager = LinearLayoutManager(context)
        binding.btnAddSchedule.setOnClickListener {
            onEditInterface(Schedule(-1, "00", ArrayList(), "Pattern", LocalDate.now()))
        }
        return binding.root
    }

    override fun onEditInterface(schedule: Schedule) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ScheduleEditorFragment(schedule, this))
            .addToBackStack("ScheduleEditor")
            .commit()
    }

    override fun onDeleteSchedule(schedule: Schedule) {
        val toBeRemovedIndex = schedules.indexWithMatchingId(schedule.id)
        schedules.remove(schedule)
        rvadapter.notifyItemRemoved(toBeRemovedIndex)
    }

    override fun onCreateSchedule(schedule: Schedule) {
        schedules.add(schedule)
        rvadapter.notifyItemInserted(schedules.size - 1)
    }

    override fun onUpdateSchedule(schedule: Schedule) {
        val toBeUpdatedIndex = schedules.indexWithMatchingId(schedule.id)
        schedules[toBeUpdatedIndex] = schedule
        rvadapter.notifyItemChanged(toBeUpdatedIndex)
    }

    fun MutableList<Schedule>.indexWithMatchingId(id: Int): Int {
        var matchingIndex = 0
        for (index in 0 until this.size) {
            if (this[index].id == id) {
                matchingIndex = index
                break
            }
        }
        return matchingIndex
    }
}