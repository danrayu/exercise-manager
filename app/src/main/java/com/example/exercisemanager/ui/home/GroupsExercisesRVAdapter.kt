package com.example.exercisemanager.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.ItemExerciseBinding
import com.example.exercisemanager.databinding.ItemGroupBinding
import com.example.exercisemanager.databinding.ItemUneditableExerciseBinding
import com.example.exercisemanager.databinding.ItemUneditableGroupBinding
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.exercises.ExerciseERVUneditableAdapter
import com.example.exercisemanager.ui.groups.Group


class GroupsExercisesRVAdapter(private val itemList: MutableList<DisplayableItem>,
                               val context: Context,
                               private val listener: AdapterElementInterface)
    : RecyclerView.Adapter<GroupsExercisesRVAdapter.BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    interface AdapterElementInterface {
        fun onRemoveElementCall(position: Int)
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    abstract class BaseViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: DisplayableItem, position: Int)
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
        BaseViewHolder(binding) {
            override fun bind(item: DisplayableItem, position: Int) {
                with(item as Exercise) {

                    binding.tvExerciseName.text = this.name

                    binding.tvEdescription.text = this.description

                    binding.rlExpandedEdescription.visibility =
                        if (this.expand) View.VISIBLE else View.GONE
                    binding.btnEditExercise.setImageResource(R.drawable.ic_close)
                    binding.btnEditExercise.setOnClickListener {
                        listener.onRemoveElementCall(position)
                    }
                    binding.cardLayout.setOnClickListener {
                        this.expand = !this.expand
                        notifyItemChanged(position)
                    }
                }
            }
        }

    inner class GroupViewHolder(private val binding: ItemGroupBinding) :
        BaseViewHolder(binding) {
        override fun bind(item: DisplayableItem, position: Int) {
            with(item as Group) {

                // setting up child rv containing exercises
                val layoutManager = LinearLayoutManager(
                    binding.rvGroupDisplay.context,
                    LinearLayoutManager.VERTICAL,
                    false)
                layoutManager.initialPrefetchItemCount = binding.groupCardLayoutG.childCount
                layoutManager.initialPrefetchItemCount = this.exercises.size
                val childRVExercisesAdapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rvGroupDisplay.layoutManager = layoutManager
                binding.rvGroupDisplay.adapter = childRVExercisesAdapter
                binding.rvGroupDisplay.setRecycledViewPool(viewPool)
                binding.btnEditGroup.setImageResource(R.drawable.ic_close)

                // Layout elements setup
                binding.tvGroupName.text = this.name
                binding.tvGdescription.text = this.description
                binding.rvGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedGroup.visibility = if (this.expanded) View.VISIBLE else View.GONE
                binding.btnEditGroup.setOnClickListener {
                    listener.onRemoveElementCall(position)
                }
                binding.groupCardLayoutG.setOnClickListener {
                    this.expanded = !this.expanded
                    notifyItemChanged(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return ExerciseViewHolder(ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return GroupViewHolder( ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ONE) {

            holder.bind(itemList[position], position)
        }
        else {
            with (holder as GroupViewHolder) {
                this.bind(itemList[position], position)
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