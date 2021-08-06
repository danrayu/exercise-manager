package com.example.exercisemanager.ui.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemExerciseBinding

class ExerciseERVAdapter(
    private val exerciseList: MutableList<Exercise>,
    private val callback: EditEventInterface
) : RecyclerView.Adapter<ExerciseERVAdapter.ViewHolder>() {

    interface EditEventInterface {
        fun editButtonPressed(exerciseIndex: Int)
    }

    inner class ViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(exerciseList[position]){
                binding.tvExerciseName.text = this.name
                binding.tvEdescription.text = this.description
                binding.rlExpandedEdescription.visibility = if (this.expand) View.VISIBLE else View.GONE

                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyItemChanged(position)
                }
                binding.btnEditExercise.setOnClickListener {
                    callback.editButtonPressed(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}