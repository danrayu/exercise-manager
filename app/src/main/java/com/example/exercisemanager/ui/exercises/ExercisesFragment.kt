package com.example.exercisemanager.ui.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.R
import com.example.exercisemanager.databinding.FragmentExercisesBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.src.ExerciseManagerUtility
import com.example.exercisemanager.ui.muscles.Muscle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExercisesFragment : Fragment(), ExerciseCreatorDialogueFragment.CreateExerciseDialogListener,
    ExerciseEditorDialogueFragment.EditExerciseDialogListener,
    ExerciseERVAdapter.EditEventInterface {

    // View Binging
    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    // Getting DB vars and initialising
    private lateinit var db: DataBaseHandler

    // Recycler adapter variables
    private lateinit var exerciseList: MutableList<Exercise>
    private lateinit var rvAdapter: ExerciseERVAdapter

    // Dialogs setup
    private lateinit var popupInputDialogView: View
    private lateinit var dialogCreate: ExerciseCreatorDialogueFragment

    // List to be used when creating select muscle spinner
    private lateinit var muscleList: MutableList<Muscle>

    private val emu = ExerciseManagerUtility()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        exerciseList = db.readExercisesData(db.readableDatabase)
        muscleList = db.readMusclesData(db.readableDatabase)
        dialogCreate = ExerciseCreatorDialogueFragment(this, muscleList,
            db.getGroupAndExerciseNames())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExercisesBinding.inflate(layoutInflater)
        binding.rvExercisef.layoutManager = LinearLayoutManager(context)

        rvAdapter = ExerciseERVAdapter(exerciseList, this)
        binding.rvExercisef.adapter = rvAdapter

        // listens to create dialog
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_ex_creator, null)
        val btnAddExercise: FloatingActionButton = _binding!!.root.findViewById(R.id.btn_add_exercise)
        btnAddExercise.setOnClickListener {
            showCreateDialog()
        }

        return _binding!!.root

    }

    // on destroy of view make the binding reference to null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showCreateDialog() {
        dialogCreate.show(childFragmentManager, "ExerciseCreatorDialogueFragment")
    }

    override fun onCreateExerciseClick(name: String, desc: String, muscles: MutableList<Muscle>) {
        val exercise = Exercise(name, desc, muscles, id = 0)
        if (!emu.exerciseNameExists(exercise.name, exerciseList)) {
            db.insertExerciseData(db.writableDatabase, exercise)
            exerciseList.add(exercise)
            rvAdapter.notifyItemInserted(exerciseList.size - 1)
            updateRv()
        }
        else {
            Toast.makeText(context, "Name already taken", Toast.LENGTH_SHORT).show()
        }
    }

    // from ERVAdapter receives list index, creates Dialog
    override fun editButtonPressed(exerciseIndex: Int) {
        val dialogEdit = ExerciseEditorDialogueFragment(this, exerciseList[exerciseIndex],
            exerciseIndex, muscleList, db.getGroupAndExerciseNames())
        dialogEdit.show(childFragmentManager, "ExerciseEditorDialogueFragment")
    }

    // from dialog gets edited ex data and updates to db
    override fun onEditExerciseConfirm(exercise: Exercise, exerciseIndex: Int) {
        db.updateExerciseData(db.writableDatabase, exercise)
        exerciseList[exerciseIndex] = exercise
        rvAdapter.notifyItemChanged(exerciseIndex)
        updateRv()
    }


    // from adapter
    override fun onExerciseDeleteClick(dialog: DialogFragment, exerciseIndex: Int) {
        dialog.dismiss()
        db.deleteExerciseItem(db.writableDatabase, exerciseList[exerciseIndex])
        exerciseList.removeAt(exerciseIndex)
        rvAdapter.notifyItemRemoved(exerciseIndex)
        updateRv()
    }

    private fun updateRv() {
        exerciseList = db.readExercisesData(db.readableDatabase)
        rvAdapter = ExerciseERVAdapter(exerciseList, this)
        binding.rvExercisef.adapter = rvAdapter
    }
}
