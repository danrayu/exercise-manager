package com.example.exercisemanager.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.GroupItemBinding
import kotlinx.android.synthetic.main.group_item.view.*

class GroupsRVAdapter(
    private val groupList: MutableList<Group>,
    private val callback: EditEventInterface
) : RecyclerView.Adapter<GroupsRVAdapter.ViewHolder>() {

    private lateinit var rvAdapter: ExerciseERVUneditableAdapter

    interface EditEventInterface {
        fun editGroupButtonPressed(group: Group, isNew:Boolean)
    }

    inner class ViewHolder(val binding: GroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView : RecyclerView = itemView.rv_group_display
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(groupList[position]) {

                rvAdapter = ExerciseERVUneditableAdapter(groupList[position].exercises)
                holder.recyclerView.adapter = rvAdapter

                binding.tvGroupName.text = this.name
                binding.tvGdescription.text = this.description
                binding.rvGroupDisplay.adapter = ExerciseERVUneditableAdapter(this.exercises)
                binding.rlExpandedGroup.visibility = if (this.expanded) View.VISIBLE else View.GONE

                binding.btnEditGroup.setOnClickListener {
                    callback.editGroupButtonPressed(this, false)
                }
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