package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.databinding.DialogExCreatorBinding
import com.example.exercisemanager.databinding.DialogFilterDisplayableItemsBinding
import com.example.exercisemanager.ui.muscles.Muscle

class DialogSortFilter(private val listener: NotifyCategoriesApplied) : DialogFragment() {

    private lateinit var categories: Categories
    private val targetMuscles: MutableList<Muscle> = ArrayList()

    interface NotifyCategoriesApplied {
        fun onApplyCategoriesPressed(categories: Categories)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogFilterDisplayableItemsBinding.inflate(LayoutInflater.from(context))
            var spinnerTypeIncluded = binding.spItemTypeIncluded
            val spinnerTypeOrder = binding.spTypeOrder
            val spinnerSortOrder = binding.spSortOrder

            // setting up spinners
            ArrayAdapter.createFromResource(
                requireContext(),
                com.example.exercisemanager.R.array.list_included,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTypeIncluded.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                com.example.exercisemanager.R.array.list_type_order,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTypeOrder.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                com.example.exercisemanager.R.array.list_order,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSortOrder.adapter = adapter
            }

            categories =
                Categories(Order.Descending, TypeOrder.Mixed, Included.DisplayableItem, targetMuscles)

            binding.btnCloseSortDialog.setOnClickListener {
                this.dismiss()
            }
            binding.btnSaveSortFilter.setOnClickListener {
                categories = Categories(
                    when (spinnerSortOrder.selectedItem.toString())
                    {
                        "Ascending" -> Order.Ascending
                        else -> Order.Descending
                    },
                    when (spinnerTypeOrder.selectedItem.toString()) {
                        "Exercises first" -> TypeOrder.ExercisesFirst
                        "Groups first" -> TypeOrder.GroupsFirst
                        else -> TypeOrder.Mixed
                    },
                    when (spinnerTypeIncluded.selectedItem.toString()) {
                        "All" -> Included.DisplayableItem
                        "Exercises" -> Included.Exercise
                        else -> Included.Group
                    }, targetMuscles)
                listener.onApplyCategoriesPressed(categories)
                this.dismiss()
            }

            spinnerTypeIncluded.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
                    if (p0 != null) {
                        when (p0.getItemAtPosition(p2).toString()) {
                            "Exercises" -> ArrayAdapter.createFromResource(
                                requireContext(),
                                com.example.exercisemanager.R.array.type_order_exercises,
                                android.R.layout.simple_spinner_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerTypeOrder.adapter = adapter
                            }
                            "Groups" -> ArrayAdapter.createFromResource(
                                requireContext(),
                                com.example.exercisemanager.R.array.type_order_groups,
                                android.R.layout.simple_spinner_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerTypeOrder.adapter = adapter
                            }
                            else -> ArrayAdapter.createFromResource(
                                requireContext(),
                                com.example.exercisemanager.R.array.list_type_order,
                                android.R.layout.simple_spinner_item
                            ).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerTypeOrder.adapter = adapter
                            }
                        }
                    }
                }
            }

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}