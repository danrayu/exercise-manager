package com.example.exercisemanager.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.GroupItemBinding

class GroupsRVAdapter(
    private val groupList: MutableList<Group>,
    private val callback: EditEventInterface
) : RecyclerView.Adapter<GroupsRVAdapter.ViewHolder>() {

    interface EditEventInterface {
        fun editGroupButtonPressed(group: Group)
    }

    inner class ViewHolder(val binding: GroupItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(groupList[position]) {
                binding.tvGroupName.text = this.name
                binding.tvGdescription.text = this.description
                binding.rvGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedGroup.visibility = if (this.expanded) View.VISIBLE else View.GONE
                binding.btnEditGroup.setOnClickListener {
                    callback.editGroupButtonPressed(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }
}