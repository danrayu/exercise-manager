package com.example.exercisemanager.ui.muscles

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisemanager.databinding.FragmentMusclesBinding
import com.example.exercisemanager.src.DataBaseHandler
import com.example.exercisemanager.ui.elements.MuscleCreatorDialogueFragment
import com.example.exercisemanager.ui.elements.MuscleEditorDialogueFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MusclesFragment : Fragment(), MusclesRVAdapter.EditEventInterface,
    MuscleCreatorDialogueFragment.CreateMuscleDialogListener,
    MuscleEditorDialogueFragment.EditMuscleDialogListener {

    // View Binging
    private var _binding: FragmentMusclesBinding? = null
    private val binding get() = _binding!!

    // Getting DB vars and initialising
    private lateinit var db: DataBaseHandler

    // Recycler adapter variables
    private lateinit var rvAdapter: MusclesRVAdapter

    // List to be used when creating select muscle spinner
    private lateinit var muscleList: MutableList<Muscle>
    private var muscleNameList: MutableList<String> = ArrayList()

    private lateinit var dialogCreate: MuscleCreatorDialogueFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = DataBaseHandler(context)
        muscleList = db.readMusclesData(db.readableDatabase)
        for (muscle in muscleList) {
            muscleNameList.add(muscle.name)
        }
        dialogCreate = MuscleCreatorDialogueFragment(this, muscleNameList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMusclesBinding.inflate(layoutInflater)
        binding.rvMusclesf.layoutManager = LinearLayoutManager(context)

        rvAdapter = MusclesRVAdapter(muscleList, this)
        binding.rvMusclesf.adapter = rvAdapter
        val btnAddMuscle: FloatingActionButton = _binding!!.root.findViewById(com.example.exercisemanager.R.id.fab_add_muscle)
        btnAddMuscle.setOnClickListener {
            showCreateDialog()
        }

        return _binding!!.root

    }

    // from ERVAdapter receives list index, edits Muscle
    override fun editMuscleButtonPressed(muscle: Muscle, muscleIndex: Int) {
        val dialogEdit = MuscleEditorDialogueFragment(this, muscle, muscleIndex)
        dialogEdit.show(parentFragmentManager, "ExerciseEditorDialogueFragment")
    }

    // from DialogCreator
    override fun onCreateMuscleClick(name: String) {
        db.insertMusclesData(db.writableDatabase, name)
        muscleList.add(Muscle(name, 0))
        rvAdapter.notifyItemInserted(muscleList.size - 1)
    }

    private fun showCreateDialog() {
        dialogCreate.show(parentFragmentManager, "ExerciseCreatorDialogueFragment")
    }

    override fun onEditMuscleConfirm(muscle: Muscle) {
        db.updateMusclesData(db.writableDatabase, muscle)
        muscleList = db.readMusclesData(db.readableDatabase)
        rvAdapter.notifyDataSetChanged()
    }

    override fun onMuscleDeleteClick(dialog: DialogFragment, muscleIndex: Int) {
        dialog.dismiss()
        db.deleteMuscleItem(db.writableDatabase, muscleList[muscleIndex])
        muscleList.removeAt(muscleIndex)
        rvAdapter.notifyItemRemoved(muscleIndex)
    }

    // on destroy of view make the binding reference to null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}