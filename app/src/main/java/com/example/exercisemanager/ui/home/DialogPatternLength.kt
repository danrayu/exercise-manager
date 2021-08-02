package com.example.exercisemanager.ui.home

import android.app.Dialog
import android.content.DialogInterface
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
private const val SCROLL_DX = 5
private const val DIRECTION_RIGHT = 1

class DialogPatternLength(private val listener: OnLengthSelected) : DialogFragment(), PatternLengthLVA.OnElementPress {

    interface OnLengthSelected {
        fun onLengthSelected(length: Int)
    }

    lateinit var recyclerView: RecyclerView
    private val lengthOptions = (2..14).toMutableList()
    lateinit var rvAdapter: PatternLengthLVA

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogPageViewSliderBinding.inflate(LayoutInflater.from(context))
            recyclerView = binding.rvPatternLength
            val snapHelper = LinearSnapHelper()
            rvAdapter = PatternLengthLVA(this)
            setupRV()

            snapHelper.attachToRecyclerView(recyclerView)
            builder.setView(binding.root)
                .setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onLengthSelected(recyclerView.layoutManager!!.
                        getPosition(snapHelper.findSnapView(recyclerView.layoutManager)!!) + 1)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        this.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun setupRV() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false)
            adapter = rvAdapter
        }
        rvAdapter.submitList(lengthOptions+lengthOptions)
        recyclerView.scrollToPosition(lengthOptions.size)
        lifecycleScope.launch { autoScrollList() }
    }

    private tailrec suspend fun autoScrollList() {
        val currentList = rvAdapter.currentList
        if (!recyclerView.canScrollHorizontally(DIRECTION_RIGHT)) {
            val firstPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val lastPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (firstPosition != RecyclerView.NO_POSITION) {
                val firstPart = currentList.subList(0, firstPosition)
                val mainPart = currentList.subList(firstPosition, currentList.size)
                val thirdPart = currentList.subList(0, currentList.size)
                rvAdapter.submitList(firstPart + mainPart + thirdPart)
            }
        }
        delay(DELAY_BETWEEN_SCROLL_MS)
        autoScrollList()
    }

    override fun onElementPress(position: Int) {
        val firstPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        recyclerView.smoothScrollToPosition(position)
    }

    private fun createList(currentList: MutableList<Int>, firstPosition: Int) : MutableList<Int> {
        return (currentList.subList(0, currentList.size) +
                currentList.subList(firstPosition, currentList.size) +
                currentList.subList(0, currentList.size)).toMutableList()
    }
}