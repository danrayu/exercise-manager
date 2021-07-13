package com.example.exercisemanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R

class HomePagerAdapter(private val tabNames: MutableList<String>) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.pageName.text = tabNames[position]
    }

    override fun getItemCount() = tabNames.size
}

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val pageName: TextView = view.findViewById(R.id.tv_home_pager_item)
}