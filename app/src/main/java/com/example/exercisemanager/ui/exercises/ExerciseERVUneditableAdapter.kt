package com.example.exercisemanager.ui.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ExerciseItemUneditableBinding

class ExerciseERVUneditableAdapter(
    private val exerciseList: MutableList<Exercise>
) : RecyclerView.Adapter<ExerciseERVUneditableAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ExerciseItemUneditableBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExerciseItemUneditableBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(exerciseList[holder.adapterPosition]) {

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

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}