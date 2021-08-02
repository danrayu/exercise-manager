package com.example.exercisemanager.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemPickableCircleBinding

class PatternLengthLVA(private val callback: OnElementPress) : ListAdapter<Int, PatternLengthLVA.IntViewHolder>(IntDiffCallback()) {

    interface OnElementPress {
        fun onElementPress(position: Int)
    }

    inner class IntViewHolder(val binding: ItemPickableCircleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntViewHolder {
        val binding = ItemPickableCircleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return IntViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntViewHolder, position: Int) {
        with(holder) {
            binding.tvNumber.text = getItem(position).toString()
            binding.muscleCardLayout.setOnClickListener {
                callback.onElementPress(position)
            }
        }
    }
}

class IntDiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem
}