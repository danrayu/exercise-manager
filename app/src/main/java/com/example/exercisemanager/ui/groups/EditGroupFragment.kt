package com.example.exercisemanager.ui.groups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentGroupsBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.ui.exercises.Exercise

class EditGroupFragment(group: Group) : Fragment(), GroupsRVAdapter.EditEventInterface {

    // View Binging
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DataBaseHandler

    private var exerciseList = group.exercises
    private lateinit var rvAdapter: GroupsRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsBinding.inflate(layoutInflater)
        binding.rvGroups.layoutManager = LinearLayoutManager(context)

        rvAdapter = GroupsRVAdapter(exerciseList, this)
        binding.rvGroups.adapter = rvAdapter

        return _binding!!.root
    }

    override fun editGroupButtonPressed(group: Group) {
        val fragment = EditGroupFragment()
        val manager : FragmentManager = childFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.cl_groups_fragment, fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}