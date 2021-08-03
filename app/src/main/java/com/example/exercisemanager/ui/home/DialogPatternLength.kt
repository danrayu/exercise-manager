package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.DialogPageViewSliderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_BETWEEN_SCROLL_MS = 25L

class DialogPatternLength(private val listener: OnLengthSelected) : DialogFragment(), PatternLengthLVA.OnElementPress {

    interface OnLengthSelected {
        fun onLengthSelected(length: Int)
    }

    lateinit var recyclerView: RecyclerView
    private val lengthOptions = (2..14).toMutableList()
    private lateinit var rvAdapter: PatternLengthLVA
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogPageViewSliderBinding.inflate(LayoutInflater.from(context))
            recyclerView = binding.rvPatternLength
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = LinearSnapHelper()
            rvAdapter = PatternLengthLVA(this)
            setupRV()

            snapHelper.attachToRecyclerView(recyclerView)
            builder.setView(binding.root)
                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    val index = recyclerView.layoutManager!!.getPosition(snapHelper.findSnapView(recyclerView.layoutManager)!!) + 2
                    listener.onLengthSelected(
                        if (index < lengthOptions.size) index else index - lengthOptions.size)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    this.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun setupRV() {
        with(recyclerView) {
            this.layoutManager = this@DialogPatternLength.layoutManager
            adapter = rvAdapter
        }
        rvAdapter.submitList(lengthOptions+lengthOptions)
        recyclerView.scrollToPosition(lengthOptions.size - 2)
        lifecycleScope.launch { autoScrollList() }
    }

    private tailrec suspend fun autoScrollList() {
        val firstPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (firstPosition < 3) {
            recyclerView.scrollToPosition((firstPosition + lengthOptions.size + 4))
            recyclerView.smoothScrollBy(40, 0)
        }
        if (firstPosition > 18) {
            recyclerView.scrollToPosition((firstPosition - lengthOptions.size))
            recyclerView.smoothScrollBy(40, 0)
        }

        delay(DELAY_BETWEEN_SCROLL_MS)
        autoScrollList()
    }

    override fun onElementPress(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }
}