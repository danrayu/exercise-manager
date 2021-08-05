package com.example.exercisemanager.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.BtnCircleBinding
import com.example.exercisemanager.databinding.ItemPickableCircleBinding

class IntDiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem
}

class BoolDiffCallback : DiffUtil.ItemCallback<Boolean>() {
    override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean): Boolean =
        oldItem == newItem
}

class PatternLengthLVA(private val callback: OnElementPress)
    : ListAdapter<Int, PatternLengthLVA.IntViewHolder>(IntDiffCallback()) {

    interface OnElementPress {
        fun onElementPress(position: Int)
    }

    inner class IntViewHolder(val binding: ItemPickableCircleBinding)
        : RecyclerView.ViewHolder(binding.root)

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


class SelectableBubblesLVA(private val callback: OnElementPress, val context: Context)
    : ListAdapter<Boolean, SelectableBubblesLVA.BoolViewHolder>(BoolDiffCallback()) {

    interface OnElementPress {
        fun onElementPress(position: Int)
    }

    inner class BoolViewHolder(val binding: BtnCircleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoolViewHolder {
        val binding = BtnCircleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoolViewHolder, position: Int) {
        with(holder) {
            binding.btnCircular.textOn = (position + 1).toString()
            binding.btnCircular.textOff = (position + 1).toString()
            val list = this@SelectableBubblesLVA.currentList
            binding.btnCircular.isChecked = this@SelectableBubblesLVA.currentList[position]
            if (binding.btnCircular.isChecked) {
                binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
            }
            else {
                binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.background_gray))
            }
            binding.btnCircular.setOnCheckedChangeListener { _, isChecked ->
                binding.btnCircular.isChecked = this@SelectableBubblesLVA.currentList[position]
                callback.onElementPress(position)

                if (isChecked) {
                    binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
                }
                else {
                    binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.background_gray))
                }
            }
         }
    }
}

class SelectableBubblesWeekLVA(private val callback: OnElementPress, val context: Context)
    : ListAdapter<Boolean, SelectableBubblesWeekLVA.BoolViewHolder>(BoolDiffCallback()) {

    private val weekdays: MutableList<String> = ArrayList()

    interface OnElementPress {
        fun onElementPress(position: Int)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        weekdays.add("Mon")
        weekdays.add("Tue")
        weekdays.add("Wed")
        weekdays.add("Thu")
        weekdays.add("Fri")
        weekdays.add("Sat")
        weekdays.add("Sun")
    }

    inner class BoolViewHolder(val binding: BtnCircleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoolViewHolder {
        val binding = BtnCircleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoolViewHolder, position: Int) {
        with(holder) {
            binding.btnCircular.textOn = weekdays[position]
            binding.btnCircular.textOff = weekdays[position]
            binding.btnCircular.isChecked = this@SelectableBubblesWeekLVA.currentList[position]
            binding.btnCircular.setOnCheckedChangeListener { _, isChecked ->
                callback.onElementPress(position)
                if (isChecked) {
                    binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
                }
                else {
                    binding.btnCircular.setBackgroundColor(ContextCompat.getColor(context, R.color.background_gray))
                }
            }
        }
    }
}

