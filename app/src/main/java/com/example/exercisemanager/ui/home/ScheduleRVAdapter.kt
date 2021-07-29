package com.example.exercisemanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemExerciseBinding

class ScheduleRVAdapter(private val schedules: MutableList<Schedule>, private val callback: OnEditSchedule) : RecyclerView.Adapter<ScheduleRVAdapter.ViewHolder>() {

    interface OnEditSchedule {
        fun onEditInterface(schedule: Schedule)
    }

    // item exercise layout is being used due to same functionality being needed
    inner class ViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseBinding.inflate(LayoutInflater.
        from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(schedules[position]) {
                var expanded = false
                var elementText = ""
                for (item in this.displayableItems!!) {
                    elementText = elementText + item.name + "\n"
                }
                binding.tvExerciseName.text = elementText

                binding.btnEditExercise.setOnClickListener {
                    callback.onEditInterface(schedules[position])
                }

                binding.rlExpandedEdescription.visibility = if (expanded) View.VISIBLE else View.GONE

                binding.cardLayout.setOnClickListener {
                    expanded = !expanded
                }
            }
        }    }

    override fun getItemCount(): Int {
        return schedules.size
    }
}