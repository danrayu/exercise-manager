package com.example.exercisemanager.ui.searchable_spinner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.databinding.ItemSsElementBinding
import com.example.exercisemanager.src.DisplayableItem

class SearchableSpinnerSelectionRVAdapter
    (private var displayableItemList: MutableList<DisplayableItem>,
     private val callback: PressEventInterface)
    : RecyclerView.Adapter<SearchableSpinnerSelectionRVAdapter.ViewHolder>() {

    interface PressEventInterface {
        fun elementPressed(item: DisplayableItem)
    }

    inner class ViewHolder(val binding: ItemSsElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSsElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(displayableItemList[position]){
                binding.tvSsElementName.text = this.name

                binding.clSsElement.setOnClickListener {
                    callback.elementPressed(this)
                    displayableItemList.remove(this)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return displayableItemList.size
    }
}