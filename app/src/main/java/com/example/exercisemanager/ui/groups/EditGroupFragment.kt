package com.example.exercisemanager.ui.groups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentGroupEditBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.searchable_spinner.SearchableSpinnerDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class EditGroupFragment(private var group: Group, private var isNew: Boolean) : Fragment(),
    GroupExercisesRVAdapter.EditEventInterface,
    SearchableSpinnerDialog.OnElementPressed {

    // View Binging
    private var _binding: FragmentGroupEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DataBaseHandler

    private lateinit var rvAdapter: GroupExercisesRVAdapter
    private lateinit var allExercises: MutableList<Exercise>
    private lateinit var dialogSelect: SearchableSpinnerDialog


    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        allExercises = db.readExercisesData(db.readableDatabase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupEditBinding.inflate(inflater)
        binding.rvGroupExercises.layoutManager = LinearLayoutManager(context)

        rvAdapter = GroupExercisesRVAdapter(group.exercises, this)
        binding.rvGroupExercises.adapter = rvAdapter

        _binding!!.etGroupName.setText(group.name)
        _binding!!.etGroupDescription.setText(group.description)

        binding.btnAddGroupExercise.setOnClickListener {
            showSelectDialog()
        }

        val saveGroupFAB : FloatingActionButton = _binding!!.root.findViewById(R.id.btn_save_group)
        saveGroupFAB.setOnClickListener {
            group.name = _binding!!.etGroupName.text.toString()
            group.description = _binding!!.etGroupDescription.text.toString()
            saveGroup(group)
            this.isNew = false
        }

        return binding.root
    }

    private fun showSelectDialog() {
        dialogSelect = SearchableSpinnerDialog(this, allExercises.toMutableList())
        dialogSelect.show(parentFragmentManager, "ExerciseCreatorDialogueFragment")
    }

    override fun removeButtonPressed(exercise: Exercise, exerciseIndex: Int) {
        group.exercises.removeAt(exerciseIndex)
        rvAdapter.notifyItemRemoved(exerciseIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun saveGroup(group: Group) {
        if (isNew) {
            db.insertGroupData(db.writableDatabase, group)
        }
        else {
            db.updateGroupData(db.writableDatabase, group)
        }
    }

    override fun elementPressedInRV(item: DisplayableItem) {
        group.exercises.add(item as Exercise)
        rvAdapter.notifyItemInserted(group.exercises.size-1)
    }
}