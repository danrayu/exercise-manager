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


class EditGroupFragment(private var group: Group) : Fragment(), GroupExercisesRVAdapter.EditEventInterface {

    // View Binging
    private var _binding: FragmentGroupEditBinding? = null
    private val binding get() = _binding!!
    private var emu = ExerciseManagerUtility()

    private lateinit var db: DataBaseHandler

    private var exerciseList = group.exercises
    private lateinit var rvAdapter: GroupExercisesRVAdapter

    private lateinit var dialogCreate: ExerciseCreatorDialogueFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupEditBinding.inflate(layoutInflater)
        binding.rvGroupExercises.layoutManager = LinearLayoutManager(context)

        rvAdapter = GroupExercisesRVAdapter(exerciseList, this)
        binding.rvGroupExercises.adapter = rvAdapter

        val addExerciseFAB : FloatingActionButton = _binding!!.root.findViewById(R.id.btn_add_group_exercise)
        addExerciseFAB.setOnClickListener {

        }

        val saveGroupFAB : FloatingActionButton = _binding!!.root.findViewById(R.id.btn_save_group)
        saveGroupFAB.setOnClickListener {

        }

        return _binding!!.root
    }

    override fun removeButtonPressed(exercise: Exercise, exerciseIndex: Int) {
        exerciseList.removeAt(exerciseIndex)
        rvAdapter.notifyItemRemoved(exerciseIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}