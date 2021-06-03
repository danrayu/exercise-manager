package com.example.exercisemanager.ui.elements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.MusclePickerItemBinding
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.muscles.MusclesRVAdapter

class ExerciseMuscleRVAdapter(private val muscleList: MutableList<Muscle>,
                              callback: ExerciseMuscleRVAdapter.UnpickEventInterface)
    : RecyclerView.Adapter<ExerciseMuscleRVAdapter.ViewHolder>() {

    private val unpickPressedCallback = callback

    interface UnpickEventInterface {
        fun unpickMuscleButtonPressed(muscleIndex: Int)
    }

    inner class ViewHolder(val binding: MusclePickerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseMuscleRVAdapter.ViewHolder {
        val binding = MusclePickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(muscleList[holder.adapterPosition]) {
                binding.tvMuscleItemName.text = this.name
                binding.btnUnpickMuscle.setOnClickListener {
                    unpickPressedCallback.unpickMuscleButtonPressed(holder.adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return muscleList.size
    }
}