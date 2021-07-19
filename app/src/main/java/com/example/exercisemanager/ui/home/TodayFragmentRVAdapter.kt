package com.example.exercisemanager.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ExerciseItemUneditableBinding
import com.example.exercisemanager.databinding.GroupUneditableItemBinding
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.exercises.ExerciseERVUneditableAdapter
import com.example.exercisemanager.ui.groups.Group


class TodayFragmentRVAdapter(private val itemList: MutableList<Any>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class ExerciseViewHolder(private val binding: ExerciseItemUneditableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(position: Int) {
            val item = itemList[position]
            with(item as Exercise) {
                binding.tvExerciseNameUneditable.text = this.name

                binding.tvEdescriptionUneditable.text = this.description
                // binding.cardLayout.setBackgroundColor(R.color.background_dark)
                binding.rlExpandedEdescriptionUneditable.visibility =
                    if (this.expand) View.VISIBLE else View.GONE

                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }
            }
        }
    }

    inner class GroupViewHolder(private val binding: GroupUneditableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = itemList[position]
            with(item as Group) {

                // setting up child rv containing exercises
                val layoutManager = LinearLayoutManager(
                    binding.rvUnGroupDisplay.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                layoutManager.initialPrefetchItemCount = binding.groupCardLayoutSchedule.childCount
                layoutManager.initialPrefetchItemCount = item.exercises.size
                val childRVExercisesAdapter = ExerciseERVUneditableAdapter(item.exercises)
                binding.rvUnGroupDisplay.layoutManager = layoutManager
                binding.rvUnGroupDisplay.adapter = childRVExercisesAdapter
                binding.rvUnGroupDisplay.setRecycledViewPool(viewPool)

                // Layout elements setup
                binding.tvUnGroupName.text = this.name
                binding.tvUngDescription.text = this.description
                binding.rvUnGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedUnGroup.visibility =
                    if (this.expanded) View.VISIBLE else View.GONE

                binding.groupCardLayoutSchedule.setOnClickListener {
                    this.expanded = !this.expanded
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return ExerciseViewHolder(ExerciseItemUneditableBinding.inflate( LayoutInflater.from(context))  )
        }
        return GroupViewHolder( GroupUneditableItemBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ONE) {
            with (holder as ExerciseViewHolder) {
                this.bind(position)
            }
        }
        else {
            with (holder as GroupViewHolder) {
                this.bind(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList[position] is Exercise) {
            return 1
        }
        return 2
    }
}