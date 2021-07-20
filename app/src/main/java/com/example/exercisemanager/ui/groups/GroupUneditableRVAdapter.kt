package com.example.exercisemanager.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemGroupBinding
import com.example.exercisemanager.ui.exercises.ExerciseERVUneditableAdapter

class GroupUneditableRVAdapter(
    private val groupList: MutableList<Group>,
) : RecyclerView.Adapter<GroupUneditableRVAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class ViewHolder(val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(groupList[position]) {

                // setting up child rv containing exercises
                val layoutManager: LinearLayoutManager? = LinearLayoutManager(
                    holder.binding.rvGroupDisplay.context,
                    LinearLayoutManager.VERTICAL,
                    false)
                layoutManager!!.initialPrefetchItemCount = binding.groupCardLayoutG.childCount
                layoutManager.initialPrefetchItemCount = groupList[position].exercises.size
                val childRVExercisesAdapter = ExerciseERVUneditableAdapter(groupList[position].exercises)
                holder.binding.rvGroupDisplay.layoutManager = layoutManager
                holder.binding.rvGroupDisplay.adapter = childRVExercisesAdapter
                holder.binding.rvGroupDisplay.setRecycledViewPool(viewPool)

                // Layout elements setup
                binding.tvGroupName.text = this.name
                binding.tvGdescription.text = this.description
                binding.rvGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedGroup.visibility = if (this.expanded) View.VISIBLE else View.GONE

                binding.groupCardLayoutG.setOnClickListener {
                    this.expanded = !this.expanded
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }
}