package com.example.exercisemanager.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exercisemanager.databinding.ExerciseItemUneditableBinding
import com.example.exercisemanager.databinding.GroupUneditableItemBinding
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.exercises.ExerciseERVUneditableAdapter
import com.example.exercisemanager.ui.groups.Group


class TodayFragmentRVAdapter(private val itemList: MutableList<DisplayableItem>, val context: Context) : RecyclerView.Adapter<TodayFragmentRVAdapter.BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    abstract class BaseViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: DisplayableItem)
    }

    inner class ExerciseViewHolder(private val binding: ExerciseItemUneditableBinding) :
        BaseViewHolder(binding) {
            override fun bind(item: DisplayableItem) {
                with(item as Exercise) {

                    binding.tvExerciseNameUneditable.text = this.name

                    binding.tvEdescriptionUneditable.text = this.description

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
        BaseViewHolder(binding) {
        override fun bind(item: DisplayableItem) {
            with(item as Group) {

                // setting up child rv containing exercises
                val layoutManager = LinearLayoutManager(
                    binding.rvUnGroupDisplay.context,
                    LinearLayoutManager.VERTICAL,
                    false)
                layoutManager.initialPrefetchItemCount = binding.groupCardLayoutSchedule.childCount
                layoutManager.initialPrefetchItemCount = this.exercises.size
                val childRVExercisesAdapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rvUnGroupDisplay.layoutManager = layoutManager
                binding.rvUnGroupDisplay.adapter = childRVExercisesAdapter
                binding.rvUnGroupDisplay.setRecycledViewPool(viewPool)

                // Layout elements setup
                binding.tvUnGroupName.text = this.name
                binding.tvUngDescription.text = this.description
                binding.rvUnGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedUnGroup.visibility = if (this.expanded) View.VISIBLE else View.GONE

                binding.groupCardLayoutSchedule.setOnClickListener {
                    this.expanded = !this.expanded
                    notifyDataSetChanged()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return ExerciseViewHolder(ExerciseItemUneditableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return GroupViewHolder( GroupUneditableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ONE) {

            holder.bind(itemList[position])
        }
        else {
            with (holder as GroupViewHolder) {
                this.bind(itemList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Exercise -> VIEW_TYPE_ONE
            is Group -> VIEW_TYPE_TWO
            else -> throw IllegalArgumentException("Invalid data type in TodayFragmentRVAdapter at position $position")
        }
    }
}