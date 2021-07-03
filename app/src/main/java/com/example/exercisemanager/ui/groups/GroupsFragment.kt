package com.example.exercisemanager.ui.groups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentGroupsBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_groups.view.*

class GroupsFragment : Fragment(), GroupsRVAdapter.EditEventInterface {

    // View Binging
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DataBaseHandler

    private lateinit var groupList: MutableList<Group>
    private lateinit var rvAdapter: GroupsRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        groupList = db.readGroupData(db.readableDatabase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsBinding.inflate(layoutInflater)
        binding.rvGroups.layoutManager = LinearLayoutManager(context)

        rvAdapter = GroupsRVAdapter(groupList, this)
        binding.rvGroups.adapter = rvAdapter

        if (groupList.size == 0) {
            _binding!!.root.tv_gempty.isVisible = true
        }

        val btnAddGroup: FloatingActionButton = _binding!!.root.findViewById(R.id.fab_add_group)
        btnAddGroup.setOnClickListener {
            val group = Group("","", ArrayList(), false, 0)
            editGroupButtonPressed(group, true)
        }
        return _binding!!.root
    }

    override fun editGroupButtonPressed(group: Group, isNew: Boolean) {
        val fragment = EditGroupFragment(group, isNew)
        val manager : FragmentManager = parentFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack( null )
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}