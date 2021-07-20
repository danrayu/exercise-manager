package com.example.exercisemanager.ui.muscles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemMuscleBinding

class MusclesRVAdapter (
    private val muscleList: MutableList<Muscle>,
    callback: EditEventInterface
) : RecyclerView.Adapter<MusclesRVAdapter.ViewHolder>() {
    private val editPressedCallback = callback

    interface EditEventInterface {
        fun editMuscleButtonPressed(muscle: Muscle, muscleIndex: Int)
    }

    inner class ViewHolder(val binding: ItemMuscleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMuscleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(muscleList[position]) {
                binding.tvMuscleName.text = this.name

                binding.btnEditMuscle.setOnClickListener {
                    editPressedCallback.editMuscleButtonPressed(muscleList[position], position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return muscleList.size
    }
}