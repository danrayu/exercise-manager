package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.exercisemanager.databinding.DialogFilterDisplayableItemsBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.muscles.Muscle
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.threeten.bp.LocalDate

class DialogSortFilter(private val listener: NotifyCategoriesApplied)
    : DialogFragment(), TargetMusclesRVA.OnRemoveElement,
    SearchableSpinnerDialog.OnElementPressed, DialogSelectReferenceDate.OnDateSelected {

    private lateinit var categories: Categories
    private val targetMuscles: MutableList<Muscle> = ArrayList()
    private lateinit var rvAdapter: TargetMusclesRVA
    private lateinit var layoutManager: FlexboxLayoutManager
    private lateinit var db: DataBaseHandler
    private lateinit var spinnerDialog: SearchableSpinnerDialog
    private lateinit var allMuscleList: MutableList<Muscle>
    private lateinit var binding: DialogFilterDisplayableItemsBinding

    interface NotifyCategoriesApplied {
        fun onApplyCategoriesPressed(categories: Categories)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(requireContext())
        allMuscleList = db.readMusclesData(db.readableDatabase)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = DialogFilterDisplayableItemsBinding.inflate(LayoutInflater.from(context))
            val spinnerTypeIncluded = binding.spItemTypeIncluded
            val spinnerTypeOrder = binding.spTypeOrder
            val spinnerSortOrder = binding.spSortOrder
            rvAdapter = TargetMusclesRVA(targetMuscles, this)
            layoutManager = FlexboxLayoutManager(requireContext())
            binding.rvTargetMuscles.adapter = rvAdapter
            binding.rvTargetMuscles.layoutManager = layoutManager
            layoutManager.flexWrap = FlexWrap.WRAP

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
            categories = Categories(Order.Descending, TypeOrder.Mixed,
                Included.DisplayableItem, targetMuscles, LocalDate.now())

            binding.btnCloseSortDialog.setOnClickListener {
                this.dismiss()
            }

            spinnerDialog = SearchableSpinnerDialog(
                this, getSpinnerList())
            binding.btnAddTargetMuscle.setOnClickListener {
                spinnerDialog.show(childFragmentManager, null)
            }

            binding.llDateSelect.setOnClickListener {
                DialogSelectReferenceDate(this).show(childFragmentManager, null)
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
                    }, targetMuscles, categories.date)
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

    override fun onRemoveElementCall(muscle: Muscle, muscleIndex: Int) {
        targetMuscles.remove(muscle)
        rvAdapter.notifyItemRemoved(muscleIndex)
        spinnerDialog = SearchableSpinnerDialog(
            this, getSpinnerList())
    }

    override fun elementPressedInRV(item: DisplayableItem) {
        targetMuscles.add(item as Muscle)
        rvAdapter.notifyItemInserted(targetMuscles.size - 1)
        spinnerDialog = SearchableSpinnerDialog(
            this, getSpinnerList())
    }

    private fun getSpinnerList() : MutableList<DisplayableItem> {
        val spinnerList: MutableList<DisplayableItem> = ArrayList()
        spinnerList.addAll(allMuscleList)
        return spinnerList.minus(targetMuscles).toMutableList()
    }

    override fun onDateSelected(date: LocalDate) {
        categories.date = date
        binding.tvDateDisplay.text = categories.date.toString()
    }
}