package com.example.exercisemanager.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.ItemExerciseBinding
import com.example.exercisemanager.ui.exercises.Exercise

class GroupExercisesRVAdapter(
    private val exerciseList: MutableList<Exercise>,
    private val callback: EditEventInterface
) : RecyclerView.Adapter<GroupExercisesRVAdapter.ViewHolder>() {

    interface EditEventInterface {
        fun removeButtonPressed(exercise: Exercise, exerciseIndex: Int)
    }

    inner class ViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(exerciseList[holder.adapterPosition]){
                binding.tvExerciseName.text = this.name

                binding.tvEdescription.text = this.description

                binding.rlExpandedEdescription.visibility = if (this.expand) View.VISIBLE else View.GONE
                binding.btnEditExercise.setImageResource(R.drawable.ic_delete)
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }
                binding.btnEditExercise.setOnClickListener {
                    callback.removeButtonPressed(exerciseList[holder.adapterPosition], holder.adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}