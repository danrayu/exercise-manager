package com.example.exercisemanager.src

import android.database.sqlite.SQLiteDatabase
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.groups.Group
import com.example.exercisemanager.ui.muscles.Muscle

class ExerciseManagerUtility {

    fun connectStringToMuscle(name: String, muscleList: MutableList<Muscle>) : Muscle {
        lateinit var correctMuscle : Muscle
        for (muscle in muscleList) {
            if (name == muscle.name) {
                correctMuscle = muscle
            }
        }
        return correctMuscle
    }

    fun connectStringToExercise(name: String, exerciseList: MutableList<Exercise>) : Exercise {
        lateinit var correctExercise : Exercise
        for (exercise in exerciseList) {
            if (name == exercise.name) {
                correctExercise = exercise
            }
        }
        return correctExercise
    }

    fun exerciseNameExists(name: String, exerciseList: MutableList<Exercise>) : Boolean {
        var foundName = false
        for (exercise in exerciseList) {
            if (exercise.name == name) {
                foundName = true
            }
        }
        return foundName
    }

    fun muscleNameExists(name: String, muscleList: MutableList<Muscle>) : Boolean {
        var foundName = false
        for (muscle in muscleList) {
            if (muscle.name == name) {
                foundName = true
            }
        }
        return foundName
    }

    fun groupExists(group : Group, databaseGroups: MutableList<Group>) : Boolean {
        for (groupIteration in databaseGroups) {
            if (groupIteration.name == group.name) {
                return true
            }
        }
        return false
    }
}