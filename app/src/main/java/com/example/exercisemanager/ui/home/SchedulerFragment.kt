package com.example.exercisemanager.ui.home

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercisemanager.databinding.FragmentEditScheduleBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerSelectionRVAdapter

class SchedulerFragment : Fragment(), SearchableSpinnerDialog.OnElementPressed {

    private lateinit var binding: FragmentEditScheduleBinding
    private lateinit var searchableSpinnerDialog: SearchableSpinnerDialog
    private lateinit var db: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBaseHandler(requireContext())
        searchableSpinnerDialog = SearchableSpinnerDialog(this, db.readMusclesData(db.readableDatabase).toMutableList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditScheduleBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun elementPressedInRV(item: DisplayableItem) {
        TODO("Not yet implemented")
    }
}