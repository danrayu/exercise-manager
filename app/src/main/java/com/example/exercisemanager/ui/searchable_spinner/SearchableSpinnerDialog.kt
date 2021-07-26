package com.example.exercisemanager.ui.searchable_spinner

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.DialogSearchableSpinnerBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem

class SearchableSpinnerDialog(private val callback: OnElementPressed,
                              private val displayableItems: MutableList<DisplayableItem>)
    : DialogFragment(),
    SearchableSpinnerSelectionRVAdapter.PressEventInterface {

    private lateinit var rvAdapter: SearchableSpinnerSelectionRVAdapter
    private lateinit var db: DataBaseHandler
    private var adapterList = displayableItems.toMutableList()
    private lateinit var binding: DialogSearchableSpinnerBinding

    interface OnElementPressed {
        fun elementPressedInRV(item: DisplayableItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSearchableSpinnerBinding.inflate(LayoutInflater.from(context))
        rvAdapter = SearchableSpinnerSelectionRVAdapter(adapterList, this)
        binding.rvSearchableSpinner.adapter = rvAdapter
        binding.rvSearchableSpinner.layoutManager = LinearLayoutManager(context)
        binding.etSs.addTextChangedListener(GenericTextWatcher())
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")

    }

    inner class GenericTextWatcher() : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            var index: Int = 0

            for (element in displayableItems) {
                if (element !in adapterList) {
                    adapterList.add(element)
                    rvAdapter.notifyItemInserted(adapterList.size-1)
                }
            }
            if (adapterList.size > 0) {
                while (true) {
                    if (!adapterList[index].name.contains(s.toString(), true)) {
                        adapterList.removeAt(index)
                        rvAdapter.notifyItemRemoved(index)
                        rvAdapter.notifyItemRangeChanged(index, adapterList.size)
                    } else {
                        index++
                    }
                    if (index == adapterList.size) {
                        break
                    }
                }
            }
        }
    }

    override fun elementPressed(item: DisplayableItem) {
        callback.elementPressedInRV(item)
        this.dismiss()
    }
}