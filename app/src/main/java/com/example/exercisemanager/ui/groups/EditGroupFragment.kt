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
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.elements.ExerciseCreatorDialogueFragment
import com.example.exercisemanager.ui.exercises.Exercise
import com.google.android.material.floatingactionbutton.FloatingActionButton


class EditGroupFragment(private var group: Group, private val isNew: Boolean) : Fragment(),
    GroupExercisesRVAdapter.EditEventInterface,
    SelectExerciseDialog.OnAddGroupExercise {

    // View Binging
    private var _binding: FragmentGroupEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DataBaseHandler

    private lateinit var rvAdapter: GroupExercisesRVAdapter
    private lateinit var allExercises: MutableList<Exercise>
    private lateinit var dialogSelect: SelectExerciseDialog


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
        _binding = FragmentGroupEditBinding.inflate(layoutInflater)
        binding.rvGroupExercises.layoutManager = LinearLayoutManager(context)

        rvAdapter = GroupExercisesRVAdapter(group.exercises, this)
        binding.rvGroupExercises.adapter = rvAdapter

        val addExerciseFAB : FloatingActionButton = _binding!!.root.findViewById(R.id.btn_add_group_exercise)
        addExerciseFAB.setOnClickListener {
            showSelectDialog()
        }

        val saveGroupFAB : FloatingActionButton = _binding!!.root.findViewById(R.id.btn_save_group)
        saveGroupFAB.setOnClickListener {
            saveGroup(group)
        }

        return _binding!!.root
    }

    private fun showSelectDialog() {
        var otherExercises: MutableList<Exercise> = ArrayList()
        otherExercises = (allExercises - group.exercises).toMutableList()
        dialogSelect = SelectExerciseDialog(this, otherExercises)
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

    override fun addGroupExercise(exercise: Exercise) {
        group.exercises.add(exercise)
        rvAdapter.notifyItemInserted(group.exercises.size-1)
    }

    private fun saveGroup(group: Group) {
        if (isNew) {
            db.insertGroupData(db.writableDatabase, group)
        }
        else {
            db.updateGroupExercisesData(db.writableDatabase, group)
        }
    }
}