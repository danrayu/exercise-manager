package com.example.exercisemanager.ui.groups

import com.example.exercisemanager.ui.exercises.Exercise

data class Group (
    var name : String,
    var description : String,
    var exercises : MutableList<Exercise>,
    var expanded: Boolean = false,
    var id : Int
)