package com.example.exercisemanager.ui.groups

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.FragmentGroupEditBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.exercises.Exercise


class EditGroupFragment(private var group: Group) : Fragment(), GroupExercisesRVAdapter.EditEventInterface {

    // View Binging
    private var _binding: FragmentGroupEditBinding? = null
    private val binding get() = _binding!!
    private var emu = ExerciseManagerUtility()

    private lateinit var db: DataBaseHandler

    private var exerciseList = group.exercises
    private lateinit var rvAdapter: GroupExercisesRVAdapter

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