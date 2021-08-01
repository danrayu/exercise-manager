package com.example.exercisemanager.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemPickableCircleBinding


const val MAX_PAGE_COUNT = 14

class PatternLengthVPAdapter : RecyclerView.Adapter<PatternLengthVPAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPickableCircleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPickableCircleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvNumber.text = (position + 1).toString()
        }
    }

    override fun getItemCount(): Int {
        return MAX_PAGE_COUNT
    }
}