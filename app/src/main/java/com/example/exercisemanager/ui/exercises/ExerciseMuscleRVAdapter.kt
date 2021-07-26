package com.example.exercisemanager.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemMusclePickerBinding
import com.example.exercisemanager.ui.muscles.Muscle

class ExerciseMuscleRVAdapter(private val muscleList: MutableList<Muscle>,
                              callback: UnpickEventInterface
)
    : RecyclerView.Adapter<ExerciseMuscleRVAdapter.ViewHolder>() {

    private val unpickPressedCallback = callback

    interface UnpickEventInterface {
        fun unpickMuscleButtonPressed(muscleIndex: Int)
    }

    inner class ViewHolder(val binding: ItemMusclePickerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMusclePickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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