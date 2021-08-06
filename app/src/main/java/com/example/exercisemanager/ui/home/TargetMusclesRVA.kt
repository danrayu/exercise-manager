package com.example.exercisemanager.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemTargetmuscleDisplayBinding
import com.example.exercisemanager.ui.muscles.Muscle

class TargetMusclesRVA(private val muscleList: MutableList<Muscle>, private val callback: OnRemoveElement) : RecyclerView.Adapter<TargetMusclesRVA.ViewHolder>() {

    interface OnRemoveElement {
        fun onRemoveElementCall(muscle: Muscle, muscleIndex: Int)
    }

    inner class ViewHolder(val binding: ItemTargetmuscleDisplayBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTargetmuscleDisplayBinding.inflate(LayoutInflater
            .from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvTargetmuscleName.text = muscleList[position].name
            binding.btnRemoveTargetmuscle.setOnClickListener {
                callback.onRemoveElementCall(muscleList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return muscleList.size
    }
}